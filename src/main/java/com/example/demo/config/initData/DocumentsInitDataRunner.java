package com.example.demo.config.initData;

import com.example.demo.entity.Document;
import com.example.demo.entity.DocumentCollection;
import com.example.demo.entity.Element;
import com.example.demo.entity.User;
import com.example.demo.repository.DocumentCollectionRepository;
import com.example.demo.repository.DocumentRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.ConfidentialityEnum;
import com.example.demo.util.DocumentCollectionTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@ConditionalOnProperty(value="init.documents.data")
@ConditionalOnBean(UsersInitDataRunner.class)
@Order(3)
public class DocumentsInitDataRunner implements ApplicationRunner {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final DocumentCollectionRepository documentCollectionRepository;
    
    public DocumentsInitDataRunner(DocumentRepository documentRepository, UserRepository userRepository,
			DocumentCollectionRepository documentCollectionRepository) {
		super();
		this.documentRepository = documentRepository;
		this.userRepository = userRepository;
		this.documentCollectionRepository = documentCollectionRepository;
	}

	@Override
    public void run(ApplicationArguments args){
        System.out.println("init documents data...");// TODO: replace with logger

        User user = userRepository.findByUsername("user");
        List<Document> createdDocuments = createUserDocuments(user, Arrays.asList("Mockito", "Java 8"), ConfidentialityEnum.PRIVATE);
        setUserFavoriteDocuments(user, createdDocuments);
        createUserDocuments(user, Arrays.asList("JUnit", "Maven"), ConfidentialityEnum.PUBLIC);
        
        User user1 = userRepository.findByUsername("user1");
        createUserDocuments(user1, Arrays.asList("Rxjs", "Spring framework"), ConfidentialityEnum.PRIVATE);
    }
    
    private void setUserFavoriteDocuments(User user, List<Document> documents) {
		DocumentCollection documentCollection = DocumentCollection.builder()
				.ownerUsername(user.getUsername())
				.documents(documents)
				.type(DocumentCollectionTypes.MY_FAVORITE_TUTOS.getName())
				.build();
		documentCollectionRepository.save(documentCollection);
	}

	private List<Document> createUserDocuments(User user, List<String> names, ConfidentialityEnum confidentiality) {
    	List<Document> createdDocuments = new ArrayList<>();
    	if(user != null && names!=null) {
            LocalDate now = LocalDate.now();
    		names.stream()
            .map(name -> Document.builder()
                    .name(name)
                    .confidentiality(confidentiality.getName())
                    .ownerUsername(user.getUsername())
                    .author(getAuthor(user.getFirstname(), user.getLastname()))
                    .creationDate(now)
                    .lastUpdateDate(now)
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
                createdDocuments.add(document);
                System.out.println("add new document "+ confidentiality.getName() +" with name: " + document.getName()+ " for user: " + user.getUsername());// TODO: replace by logger
            });
    	} else {
    		System.out.println("can't find user!");
    	}
    	return createdDocuments;
    }
    
    
    private String getAuthor(String firstname, String lastname){
    	StringBuilder stb = new StringBuilder();
    	
    	if(firstname != null){
    		stb.append(firstname);
    	}
    	if(lastname != null){
    		stb.append(" ").append(lastname);
    	}
    	return stb.toString();
    	
    }
}
