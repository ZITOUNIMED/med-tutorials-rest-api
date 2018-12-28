package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Document;

@Service
public class DocumentService {

	private List<Document> documents;
	private Long IDS = 0L;
	
	DocumentService(){
		documents = new ArrayList<Document>();
		documents.add(new Document(IDS++, "Angular 5", null));
	}

	public void addDocument(Document document) {
		document.setId(IDS++);
		documents.add(document);
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public Document getDocument(Long id) {
		return documents.stream().filter(document -> document.getId().equals(id)).findFirst().orElse(null);
	}
	
	public void saveDocument(Document document) {
		int index = documents.indexOf(document);
		documents.remove(index);
		documents.add(index, document);
	}
}
