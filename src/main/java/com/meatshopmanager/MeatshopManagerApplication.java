package com.meatshopmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MeatshopManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeatshopManagerApplication.class, args);
	}
}
