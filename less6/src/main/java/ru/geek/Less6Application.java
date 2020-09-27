package ru.geek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Less6Application {

	@Bean
	@ConditionalOnProperty(name = "my.property", havingValue = "123")
	public Object someBean(){
		return new Object();
	}

	public static void main(String[] args) {
		SpringApplication.run(Less6Application.class, args);
	}

}
