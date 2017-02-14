package com.iqmsoft.dw.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.iqmsoft.dw.boot.config.AppConfig;

@Configuration
@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.iqmsoft.dw.boot.repos")
@ComponentScan(basePackages = "com.iqmsoft.dw.boot")
@EnableAutoConfiguration
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(AppConfig.class, args);
	}
}
