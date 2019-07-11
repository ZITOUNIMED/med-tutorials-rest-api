package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.DocumentSampleDTO;
import com.example.demo.entity.Document;
import com.example.demo.repository.DocumentRepository;

@Service
public class DocumentService {

	@Autowired
	private DocumentRepository documentRepository;

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, 'read') or hasRole('ROLE_ADMIN')")
	public List<Document> getDocuments() {
		return documentRepository.findAll();
	}

	@Transactional
	public Document getDocument(Long id) {
		return documentRepository.getOne(id);
	}
	
	@Transactional
	public void saveDocument(Document document) {
		if(document != null){
			LocalDate now = LocalDate.now();
			if(document.getCreationDate() == null){
				document.setCreationDate(now);
			}
			document.setLastUpdateDate(now);
		}
		documentRepository.save(document);
	}

	public void deleteDocument(Long id) {
		documentRepository.deleteById(id);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, 'read') or hasRole('ROLE_ADMIN')")
	public List<DocumentSampleDTO> getDocumentSamples() {
		return documentRepository.findAll()
				.stream()
				.map(document -> DocumentSampleDTO.builder()
						.id(document.getId())
						.name(document.getName())
						.ownerUsername(document.getOwnerUsername())
						.confidentiality(document.getConfidentiality())
						.build())
				.collect(Collectors.toList());
	}

	public void saveAllDocuments(List<Document> documents) {
		documentRepository.saveAll(documents);
	}
}
