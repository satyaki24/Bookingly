package com.learningJavaBackend.projects.HotelBookingAndManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HotelBookingAndManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelBookingAndManagementSystemApplication.class, args);
	}

}
