package com.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 * USADO PARA NO MOSTRAR EL LOGIN WEB
 * PARA REEMPLAZAR LA EXCLUSION DESDE LA CLASE MAIN
 *  security.basic.enabled: false
  	management.security.enabled: false
 * @return
 */

@SpringBootApplication
public class AppWsExchangeRateApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppWsExchangeRateApplication.class, args);
	}

	@Bean
	RestOperations getRestOperations() {
		return new RestTemplate();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}	
	
}
