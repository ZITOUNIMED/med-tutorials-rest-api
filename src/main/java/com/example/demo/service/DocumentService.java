package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.DocumentSampleDTO;
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

	public List<DocumentSampleDTO> getDocumentSamples() {
		return documentRepository.findAll()
				.stream()
				.map(document -> DocumentSampleDTO.builder()
						.id(document.getId())
						.name(document.getName())
						.build())
				.collect(Collectors.toList());
	}
}
