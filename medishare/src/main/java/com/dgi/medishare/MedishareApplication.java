package com.dgi.medishare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MedishareApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedishareApplication.class, args);
	}


}
