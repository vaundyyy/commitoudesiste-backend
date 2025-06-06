package com.just.commitoudesiste.commitoudesiste_backend;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommitoudesisteBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommitoudesisteBackendApplication.class, args);
	}

	@PostConstruct
	public void init() {
		System.out.println("Aplicação iniciada com sucesso!");
	}
}