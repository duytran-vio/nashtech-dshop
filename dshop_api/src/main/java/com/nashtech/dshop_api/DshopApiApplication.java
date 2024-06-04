package com.nashtech.dshop_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@SpringBootApplication
@EnableJpaAuditing
public class DshopApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DshopApiApplication.class, args);
	}

}
