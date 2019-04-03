package com.example.demo;

import java.util.Arrays;import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.RoleEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.example.demo.entity.Document;
import com.example.demo.entity.Element;
import com.example.demo.repository.DocumentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MedTutorialsRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedTutorialsRestApiApplication.class, args);
	}

	@Bean
	@Profile("dev")
	public CommandLineRunner initData(DocumentRepository documentRepository,
									  RoleRepository roleRepository,
									  UserRepository userRepository,
									  PasswordEncoder passwordEncoder) {
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

			Role roleUser = new Role(RoleEnum.ROLE_USER);
			Role roleAdmin = new Role(RoleEnum.ROLE_ADMIN);

			roleRepository.save(roleUser);
			roleRepository.save(roleAdmin);

			User user = new User("user", passwordEncoder.encode("password1"), true);
			user.setRoles(Arrays.asList(roleUser));

			userRepository.save(user);

			User admin = new User("admin", passwordEncoder.encode("password2"), true);
			admin.setRoles(Arrays.asList(roleUser, roleAdmin));

			userRepository.save(admin);
		};
	}

}
