package com.api.gastos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class GastosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GastosApplication.class, args);
	}

}
