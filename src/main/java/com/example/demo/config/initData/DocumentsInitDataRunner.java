package com.example.demo.config.initData;

import com.example.demo.entity.Document;
import com.example.demo.entity.Element;
import com.example.demo.entity.User;
import com.example.demo.repository.DocumentRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.ConfidentialityEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        User user = userRepository.findByUsername("user");
        createUserDocuments(user, Arrays.asList("Mockito", "Java 8"), ConfidentialityEnum.PRIVATE);
        createUserDocuments(user, Arrays.asList("JUnit", "Maven"), ConfidentialityEnum.PUBLIC);
        
        User user1 = userRepository.findByUsername("user1");
        createUserDocuments(user1, Arrays.asList("Rxjs", "Spring framework"), ConfidentialityEnum.PRIVATE);
    }
    
    private void createUserDocuments(User user, List<String> names, ConfidentialityEnum confidentiality) {
    	if(user != null && names!=null) {
    		names.stream()
            .map(name -> Document.builder()
                    .name(name)
                    .confidentiality(confidentiality.getName())
                    .ownerUsername(user.getUsername())
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
                System.out.println("add new document "+ confidentiality.getName() +" with name: " + document.getName()+ " for user: " + user.getUsername());// TODO: replace by logger
            });
    	} else {
    		System.out.println("can't find user!");
    	}
    	
    }
}
