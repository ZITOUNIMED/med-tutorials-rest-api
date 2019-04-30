package com.example.demo.config.initData;

import com.example.demo.entity.Document;
import com.example.demo.entity.Element;
import com.example.demo.entity.User;
import com.example.demo.repository.DocumentRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
@ConditionalOnProperty(value="init.documents.data")
@ConditionalOnBean(UsersInitDataRunner.class)
@Order(3)
public class DocumentsInitDataRunner implements ApplicationRunner {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args){
        System.out.println("init documents data...");// TODO: replace with logger

        Optional<User> userOptional = userRepository.findByUsername("user");
        Stream.of("Mockito", "Java 8", "JUnit")
                .map(name -> Document.builder()
                        .name(name)
                        .owner(userOptional.orElse(null))
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
                    if(document != null && document.getOwner() != null){
                        documentRepository.save(document);
                        System.out.println("add new document with name: " + document.getName());// TODO: replace by logger
                    }
                });
    }
}
