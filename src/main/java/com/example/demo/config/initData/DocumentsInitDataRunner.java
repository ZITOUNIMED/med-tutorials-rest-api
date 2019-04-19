package com.example.demo.config.initData;

import com.example.demo.entity.Document;
import com.example.demo.entity.Element;
import com.example.demo.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
@Profile("dev")
public class DocumentsInitDataRunner implements ApplicationRunner {

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public void run(ApplicationArguments args){
        System.out.println("init documents data...");// TODO: replace with logger

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
    }
}
