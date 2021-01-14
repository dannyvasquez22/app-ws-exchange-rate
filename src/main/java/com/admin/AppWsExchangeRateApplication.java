package com.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaRepositories("com.admin.*")
@ComponentScan(basePackages = { "com.admin.*" })
@EntityScan("com.admin.*")   
public class AppWsExchangeRateApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppWsExchangeRateApplication.class, args);
	}

	@Bean
	RestOperations getRestOperations() {
		return new RestTemplate();
	}
}
