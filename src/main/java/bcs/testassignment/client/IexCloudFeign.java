package bcs.testassignment.client;

import bcs.testassignment.model.Company;
import bcs.testassignment.model.Quote;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "iexCloud", url = "${params.iex-cloud-url}")
public interface IexCloudFeign {

    @GetMapping("stock/{symbol}/company")
    public Company getCompany(@PathVariable("symbol") String symbol, @RequestParam("token") String token);

    @GetMapping("stock/{symbol}/quote/")
    public Quote getQuote(@PathVariable("symbol") String symbol, @RequestParam("token") String token);
}
