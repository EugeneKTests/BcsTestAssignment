package bcs.testassignment.handler;

import bcs.testassignment.client.IexCloudClient;
import bcs.testassignment.client.IexCloudFeign;
import bcs.testassignment.exception.IexCloudException;
import bcs.testassignment.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SplitByAllocationsService {

    private final IexCloudClient iexCloudClient;

    public StocksResponse calculate(StocksRequest stocksRequest) throws IOException, IexCloudException {
        List<Sector> sectors = buildSectorList(stocksRequest);
        Map<String, BigDecimal> valuesBySector = sectors.stream().collect(Collectors.toMap(Sector::getName, Sector::getValue, BigDecimal::add));
        BigDecimal totalValue = valuesBySector.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        List<Allocation> allocations = new ArrayList<>();
        for (Map.Entry<String, BigDecimal> entry : valuesBySector.entrySet()) {
            String sectorName = entry.getKey();
            BigDecimal value = entry.getValue();
            BigDecimal proportion = value.divide(totalValue, 5, RoundingMode.HALF_EVEN);
            allocations.add(new Allocation(sectorName, value, proportion));
        }
        StocksResponse stocksResponse = new StocksResponse(totalValue, allocations);
        return stocksResponse;
    }

    private List<Sector> buildSectorList(StocksRequest stocksRequest) {
        List<Sector> sectors = new ArrayList<>();
        for (Stock stock : stocksRequest.getStocks()) {
            String sectorName = iexCloudClient.getCompany(stock.getSymbol()).getSector();
            BigDecimal price = iexCloudClient.getQuote(stock.getSymbol()).getLatestPrice();
            BigDecimal value = price.multiply(BigDecimal.valueOf(stock.getVolume()));
            sectors.add(new Sector(sectorName, value));
        }
        return sectors;
    }
}
