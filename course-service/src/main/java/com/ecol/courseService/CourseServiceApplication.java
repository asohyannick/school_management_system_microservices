package com.ecol.courseService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.PropertySource;

@EnableDiscoveryClient
@SpringBootApplication
@PropertySource("file:${user.dir}/.env")
public class CourseServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CourseServiceApplication.class, args);
	}
}
