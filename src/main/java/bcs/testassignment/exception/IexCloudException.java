package bcs.testassignment.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class IexCloudException extends Exception {

    private HttpStatus httpStatus;
    private String symbol;

    public IexCloudException(String message, String symbol, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.symbol = symbol;
    }
}
