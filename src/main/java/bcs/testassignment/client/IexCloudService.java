package bcs.testassignment.client;

import bcs.testassignment.exception.IexCloudException;
import bcs.testassignment.model.Company;
import bcs.testassignment.model.Quote;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;

@Component
public class IexCloudService {

    @Value("${params.iex-cloud-url}")
    private String url;
    @Value("${params.token}")
    private String token;
    private IexCloud iexCloud;


    @PostConstruct
    public void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        iexCloud = retrofit.create(IexCloud.class);
    }

    public String getSector(String symbol) throws IOException, IexCloudException {
        Response<Company> response = iexCloud.getCompany(symbol, token).execute();
        if (response.isSuccessful()) {
            return response.body().getSector();
        } else {
            throw new IexCloudException(response.errorBody().string(), symbol, HttpStatus.resolve(response.code()));
        }
    }

    public BigDecimal getLatestPrice(String symbol) throws IOException, IexCloudException {
        Response<Quote> response = iexCloud.getQuote(symbol, token).execute();
        if (response.isSuccessful()) {
            return response.body().getLatestPrice();
        } else {
            throw new IexCloudException(response.errorBody().string(), symbol, HttpStatus.resolve(response.code()));
        }
    }
}
