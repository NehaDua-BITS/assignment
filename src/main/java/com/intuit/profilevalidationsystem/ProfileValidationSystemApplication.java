package com.intuit.profilevalidationsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan("com.intuit.profilevalidationsystem.model")
public class ProfileValidationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfileValidationSystemApplication.class, args);
	}

}
