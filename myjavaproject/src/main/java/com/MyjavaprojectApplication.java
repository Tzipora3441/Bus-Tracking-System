package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.persistence.autoconfigure.EntityScan;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
// @EntityScan(basePackages = {"com.example.demo.entity", "com.Models"}) //
// סריקה בשני המקומות
// @EnableJpaRepositories(basePackages = "com.example.demo.repository")
public class MyjavaprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyjavaprojectApplication.class, args);
	}

}
