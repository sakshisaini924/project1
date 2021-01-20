package com.autoscout24.AutoScout24;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.autoscout24.AutoScout24.services.ASLieteningReportServicesImpl;
import com.autoscout24.AutoScout24.services.ASListeningReportServices;

@SpringBootApplication
public class AutoScout24Application {

	public static void main(String[] args) {
		SpringApplication.run(AutoScout24Application.class, args);
	}

	@Bean
	public ASListeningReportServices getASListeningReportServices() {
		return new ASLieteningReportServicesImpl();
	}
}
