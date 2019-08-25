package bcs.testassignment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class StocksResponse {

    private BigDecimal value;
    private List<Allocation> allocations;
}
