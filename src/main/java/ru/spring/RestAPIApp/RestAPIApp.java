package ru.spring.RestAPIApp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class RestAPIApp {

	public static void main(String[] args) {
		SpringApplication.run(RestAPIApp.class, args);
	}
	@Bean
	public ModelMapper ModelMapper(){
		return new ModelMapper();
	}
}
