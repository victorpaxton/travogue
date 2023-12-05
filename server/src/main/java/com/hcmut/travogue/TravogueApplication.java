package com.hcmut.travogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class TravogueApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravogueApplication.class, args);
	}

}
