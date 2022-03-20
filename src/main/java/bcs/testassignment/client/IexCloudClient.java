package bcs.testassignment.client;

import bcs.testassignment.model.Company;
import bcs.testassignment.model.Quote;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@RequiredArgsConstructor
public class IexCloudClient {

    @Value("${params.token}")
    private String token;

    private final IexCloudFeign iexCloudFeign;

    public Company getCompany(String symbol) {
        return iexCloudFeign.getCompany(symbol, token);
    }

    public Quote getQuote(@PathVariable("symbol") String symbol) {
        return iexCloudFeign.getQuote(symbol, token);
    }
}
