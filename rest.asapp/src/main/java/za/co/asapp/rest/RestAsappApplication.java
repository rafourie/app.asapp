package za.co.asapp.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"za.co.asapp"})
@EnableDiscoveryClient
@EnableFeignClients
public class RestAsappApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestAsappApplication.class, args);
	}
}
