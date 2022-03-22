package bcs.testassignment;

import bcs.testassignment.exception.IexCloudException;
import bcs.testassignment.handler.SplitByAllocationsService;
import bcs.testassignment.model.ErrorResponse;
import bcs.testassignment.model.StocksRequest;
import bcs.testassignment.model.StocksResponse;
import bcs.testassignment.utils.ValidateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StocksController {

    private final SplitByAllocationsService splitByAllocationsHandler;

    @PostMapping(path = "/calculate", produces = "application/json")
    public ResponseEntity<?> splitByAllocation(@RequestBody StocksRequest stocksRequest) {
        if (CollectionUtils.isEmpty(stocksRequest.getStocks())) {
            return new ResponseEntity<>(new ErrorResponse("Stocks is empty"), HttpStatus.BAD_REQUEST);
        }
        String errorMessage = ValidateUtils.validateStocksRequest(stocksRequest);
        if (errorMessage != null) {
            return new ResponseEntity<>(new ErrorResponse(errorMessage), HttpStatus.BAD_REQUEST);
        }
        StocksResponse stocksResponse;
        try {
            stocksResponse = splitByAllocationsHandler.calculate(stocksRequest);
        } catch (IOException e) {
            return new ResponseEntity<>(new ErrorResponse("Error accessing external service"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IexCloudException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage() + " for " + e.getSymbol()), e.getHttpStatus());
        }
        return new ResponseEntity<>(stocksResponse, HttpStatus.OK);
    }
}
