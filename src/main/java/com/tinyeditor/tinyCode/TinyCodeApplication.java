package com.tinyeditor.tinyCode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class TinyCodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TinyCodeApplication.class, args);
	}

}
