package com.cti.npmeetup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NPMeetupApplication {

	public static void main(String[] args) {
		// To change root context path of web app
		//System.setProperty("server.servlet.context-path", "/home");
		SpringApplication.run(NPMeetupApplication.class, args);
	}
}
