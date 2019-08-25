package bcs.testassignment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Quote {

    @SerializedName("latestPrice")
    @Expose
    private BigDecimal latestPrice;
}
