package bcs.testassignment.utils;

import bcs.testassignment.model.Stock;
import bcs.testassignment.model.StocksRequest;
import org.springframework.util.StringUtils;

public class ValidateUtils {

    public static String validateStocksRequest(StocksRequest request) {
        String errorMessage;
        for (Stock stock : request.getStocks()) {
            errorMessage = validateStock(stock);
            if (errorMessage != null) {
                return errorMessage;
            }
        }
        return null;
    }

    private static String validateStock(Stock stock) {
        if (StringUtils.isEmpty(stock.getSymbol())) {
            return "Symbol is null or empty";
        }
        if (stock.getVolume() == null || stock.getVolume() <= 0) {
            return "Volume must be positive for Symbol " + stock.getSymbol();
        }
        return null;
    }
}
