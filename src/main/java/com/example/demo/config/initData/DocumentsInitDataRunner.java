package com.example.demo.config.initData;

import com.example.demo.entity.Document;
import com.example.demo.entity.Element;
import com.example.demo.entity.User;
import com.example.demo.repository.DocumentRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.ConfidentialityEnum;
import com.example.demo.util.ElementTypeEnum;
import com.example.demo.util.dto.MultiChoiceQuestionDTO;
import com.example.demo.util.dto.OneChoiceQuestionDTO;
import com.example.demo.util.dto.TextQuestionDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@ConditionalOnProperty(value="init.documents.data")
@ConditionalOnBean(UsersInitDataRunner.class)
@Order(3)
public class DocumentsInitDataRunner implements ApplicationRunner {
	private final static Logger logger = LoggerFactory.getLogger(DocumentsInitDataRunner.class);
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

	public DocumentsInitDataRunner(DocumentRepository documentRepository, UserRepository userRepository) {
		super();
		this.documentRepository = documentRepository;
		this.userRepository = userRepository;
	}

	@Override
    public void run(ApplicationArguments args){
        logger.info("init documents data...");// TODO: replace with logger

        User sourcer1 = userRepository.findByUsername("sourcer1");
        createUserDocuments(sourcer1, Arrays.asList("Mockito", "Java 8"), ConfidentialityEnum.PRIVATE);
        createUserDocuments(sourcer1, Arrays.asList("JUnit", "Maven"), ConfidentialityEnum.PUBLIC);
        
        User sourcer2 = userRepository.findByUsername("sourcer2");
        createUserDocuments(sourcer2, Arrays.asList("Rxjs", "Spring framework"), ConfidentialityEnum.PRIVATE);
    }
    
	private void createUserDocuments(User user, List<String> names, ConfidentialityEnum confidentiality) {
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
            .peek(this::initElements)
            .collect(Collectors.toList())
            .forEach(document -> {
                documentRepository.save(document);
               	logger.info("add new document "+ confidentiality.getName() +" with name: " + document.getName()+ " for user: " + user.getUsername());// TODO: replace by logger
            });
    	} else {
    		logger.warn("can't find user!");
    	}
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

    private void initElements(Document document){
        document.setElements(IntStream.range(0, 3)
                .mapToObj(number -> document.getName() + " text " + number)
                .map(text -> Element.builder()
                        .type("TEXT")
                        .text(text)
                        .page(0)
                        .row(Integer.parseInt(text.substring(text.length() - 1)))
                        .build() )
                .collect(Collectors.toList()));

        OneChoiceQuestionDTO oneChoice1 = OneChoiceQuestionDTO.builder()
            .question("One choice question?")
            .courrentAnswer("courrentAnswer")
            .correctAnswer("choice 2")
            .items(Arrays.asList("choice 1", "choice 2", "choice 3"))
            .score(1)
            .key("key1")
            .build();
        document.getElements().add(Element.builder()
            .page(0)
            .row(3)
            .text(oneChoice1.toString())
            .type(ElementTypeEnum.ONE_CHOICE_QUESTION.toString())
            .build());

        MultiChoiceQuestionDTO mutliChoice1 = MultiChoiceQuestionDTO.builder()
            .question("Multi choices question?")
            .correctAnswers(Arrays.asList("choice 1",  "choice 3"))
            .items(Arrays.asList("choice 1", "choice 2", "choice 3", "choice 4"))
            .score(2)
            .build();

        document.getElements().add(Element.builder()
            .page(0)
            .row(4)
            .text(mutliChoice1.toString())
            .type(ElementTypeEnum.MULTI_CHOICES_QUESTION.toString())
            .build());

        TextQuestionDTO shortTextQuestion = TextQuestionDTO.builder()
            .question("short text question?")
            .correctAnswer("correctAnswer")
            .courrentAnswer("courrentAnswer")
            .score(1)
            .build();

        document.getElements().add(Element.builder()
            .page(0)
            .row(5)
            .text(shortTextQuestion.toString())
            .type(ElementTypeEnum.SHORT_TEXT_QUESTION.toString())
            .build());

        TextQuestionDTO longTextQuestion = TextQuestionDTO.builder()
            .question("long text question?")
            .questionComplement("questionComplement")
            .correctAnswer("correctAnswer")
            .courrentAnswer("courrentAnswer")
            .score(2)
            .build();

        document.getElements().add(Element.builder()
            .page(0)
            .row(6)
            .text(longTextQuestion.toString())
            .type(ElementTypeEnum.LONG_TEXT_QUESTION.toString())
            .build());
    }
}
