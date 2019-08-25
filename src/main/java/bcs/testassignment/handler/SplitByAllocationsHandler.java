package bcs.testassignment.handler;

import bcs.testassignment.client.IexCloudService;
import bcs.testassignment.exception.IexCloudException;
import bcs.testassignment.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SplitByAllocationsHandler {

    IexCloudService iexCloudService;

    @Autowired
    public SplitByAllocationsHandler(IexCloudService iexCloudService) {
        this.iexCloudService = iexCloudService;
    }

    public StocksResponse calculate(StocksRequest stocksRequest) throws IOException, IexCloudException {
        List<Sector> sectors = buildSectorList(stocksRequest);
        Map<String, BigDecimal> valuesBySector = sectors.stream().collect(Collectors.toMap(Sector::getName, Sector::getValue, BigDecimal::add));
        BigDecimal totalValue = valuesBySector.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        List<Allocation> allocations = new ArrayList<>();
        for(Map.Entry<String, BigDecimal> entry: valuesBySector.entrySet()) {
            String sectorName = entry.getKey();
            BigDecimal value = entry.getValue();
            BigDecimal proportion = value.divide(totalValue, 5, RoundingMode.HALF_EVEN);
            allocations.add(new Allocation(sectorName, value, proportion));
        }
        StocksResponse stocksResponse = new StocksResponse(totalValue, allocations);
        return stocksResponse;
    }

    private List<Sector> buildSectorList(StocksRequest stocksRequest) throws IOException, IexCloudException {
        List<Sector> sectors = new ArrayList<>();
        for (Stock stock: stocksRequest.getStocks()) {
            String sectorName = iexCloudService.getSector(stock.getSymbol());
            BigDecimal price = iexCloudService.getLatestPrice(stock.getSymbol());
            BigDecimal value = price.multiply(BigDecimal.valueOf(stock.getVolume()));
            sectors.add(new Sector(sectorName, value));
        }
        return sectors;
    }
}
