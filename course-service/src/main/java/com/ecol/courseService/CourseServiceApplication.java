package com.ecol.courseService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@EnableDiscoveryClient
@SpringBootApplication
@EnableCaching
public class CourseServiceApplication {
	static void main(String[] args) {
		SpringApplication.run(CourseServiceApplication.class, args);
	}
}
