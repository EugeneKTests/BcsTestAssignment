package bcs.testassignment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class Allocation {

    private String sector;
    private BigDecimal assetValue;
    private BigDecimal proportion;
}
