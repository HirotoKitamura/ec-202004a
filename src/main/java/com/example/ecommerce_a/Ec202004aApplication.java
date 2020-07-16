package com.example.ecommerce_a;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class Ec202004aApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ec202004aApplication.class, args);
	}

}
