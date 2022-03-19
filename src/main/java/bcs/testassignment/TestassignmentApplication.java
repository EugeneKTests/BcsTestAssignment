package bcs.testassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TestassignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestassignmentApplication.class, args);
	}

}
