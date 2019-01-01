package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Document;
import com.example.demo.repository.DocumentRepository;

@Service
public class DocumentService {

	@Autowired
	private DocumentRepository documentRepository;

	public List<Document> getDocuments() {
		return documentRepository.findAll();
	}

	public Document getDocument(Long id) {
		return documentRepository.getOne(id);
	}
	
	public void saveDocument(Document document) {
		documentRepository.save(document);
	}

	public void deleteDocument(Long id) {
		documentRepository.deleteById(id);
	}
}
