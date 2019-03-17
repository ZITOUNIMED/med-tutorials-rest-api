package com.example.demo;

import java.util.Arrays;import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
			Stream.of("Mockito", "Java 8", "JUnit")
			.map(name -> Document.builder()
						.name(name)
						.build())
			.peek(document -> {
				document.setElements(IntStream.range(0, 3)
				.mapToObj(number -> document.getName() + " text " + number)
				.map(text -> Element.builder()
						.type("TEXT")
						.text(text)
						.page(0)
						.row(Integer.parseInt(text.substring(text.length() - 1)))
						.build() )
				.collect(Collectors.toList()));
			})
			.collect(Collectors.toList())
			.forEach(document -> {
				documentRepository.save(document);
			});
		};
	}

}
