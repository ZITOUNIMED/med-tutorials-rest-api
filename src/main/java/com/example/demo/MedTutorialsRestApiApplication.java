package com.example.demo;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.example.demo.entity.Document;
import com.example.demo.entity.Element;
import com.example.demo.repository.DocumentRepository;

@SpringBootApplication
public class MedTutorialsRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedTutorialsRestApiApplication.class, args);
	}

	@Bean
	@Profile("dev")
	public CommandLineRunner initData(DocumentRepository documentRepository) {
		return (args) -> {
			Document document = new Document(null, "Test", null);

			document.setElements(Arrays.asList(new Element(null, "TEXT", "Hello world", 0)));

			documentRepository.save(document);
		};
	}

}
