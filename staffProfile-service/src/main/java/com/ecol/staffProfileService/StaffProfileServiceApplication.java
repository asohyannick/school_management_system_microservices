package com.ecol.staffProfileService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@EnableDiscoveryClient
@SpringBootApplication
@EnableCaching
public class StaffProfileServiceApplication {

	static void main(String[] args) {
		SpringApplication.run(StaffProfileServiceApplication.class, args);
	}

}
