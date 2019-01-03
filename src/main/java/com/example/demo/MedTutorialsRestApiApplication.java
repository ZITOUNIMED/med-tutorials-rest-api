package com.example.demo;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.demo.entity.Document;
import com.example.demo.entity.Element;
import com.example.demo.repository.DocumentRepository;

@SpringBootApplication
public class MedTutorialsRestApiApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(MedTutorialsRestApiApplication.class, args);

		// DocumentRepository documentRepository = ctx.getBean(DocumentRepository.class);
		//
		// Document document = new Document(null, "Angular 5", null);
		//
		// document.setElements(Arrays.asList(new Element(null, "TEXT", "Hello world", 0)));
		//
		// documentRepository.save(document);


	}

}
