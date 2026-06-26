package com.springtraining.tickets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication marks the main class. It switches on auto-configuration
// and tells Spring to scan this package (and sub-packages) for components.
// We also implement ApplicationRunner so run() executes once at startup.
@SpringBootApplication
public class TicketsApplication implements ApplicationRunner {

	// A logger lets us print messages in a structured way (better than System.out.println).
	private static final Logger logger = LoggerFactory.getLogger(TicketsApplication.class);

	// @Value injects a setting from application.properties (example.message=Hello World).
	@Value("${example.message}")
	private String message;

	// The entry point: this is what actually starts the Spring Boot web server.
	public static void main(String[] args) {
		SpringApplication.run(TicketsApplication.class, args);
	}

	// Runs automatically right after the application has started.
	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("Application started with message: {}", message);
	}
}
