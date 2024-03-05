package com.rouclec.databasejpa;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log
public class DatabaseJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseJpaApplication.class, args);
	}

}
