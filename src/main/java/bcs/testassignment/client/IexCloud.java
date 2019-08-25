package bcs.testassignment.client;

import bcs.testassignment.model.Company;
import bcs.testassignment.model.Quote;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IexCloud {

    @GET("stock/{symbol}/company")
    public Call<Company> getCompany(@Path("symbol") String symbol, @Query("token") String token);

    @GET("stock/{symbol}/quote/")
    public Call<Quote> getQuote(@Path("symbol") String symbol, @Query("token") String token);
}
