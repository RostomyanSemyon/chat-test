package com.example.chattest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatTestApplication {

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(ChatTestApplication.class);
		app.setAdditionalProfiles("app");
		app.run(args);
	}

}
