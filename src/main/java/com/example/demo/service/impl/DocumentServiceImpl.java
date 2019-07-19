package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.demo.dto.DocumentSampleDTO;
import com.example.demo.entity.Document;
import com.example.demo.repository.DocumentRepository;
import com.example.demo.service.DocumentService;
import com.example.demo.util.AppPermissionTypes;

@Service
public class DocumentServiceImpl implements DocumentService {
	private final DocumentRepository documentRepository;

	public DocumentServiceImpl(DocumentRepository documentRepository) {
		super();
		this.documentRepository = documentRepository;
	}

	@Override
	public void save(Document document) {
		if(document != null){
			LocalDate now = LocalDate.now();
			if(document.getCreationDate() == null){
				document.setCreationDate(now);
			}
			document.setLastUpdateDate(now);
		}
		documentRepository.save(document);
	}

	@Override
	public Document findById(Long id) {
		return documentRepository.getOne(id);
	}

	@Override
	public void deleteById(Long id) {
		documentRepository.deleteById(id);		
	}

	@Override
	public void saveAll(List<Document> list) {
		documentRepository.saveAll(list);		
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	public List<Document> findAll() {
		return documentRepository.findAll();
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, '"+AppPermissionTypes.PUBLIC+"')")
	@Override
	public List<Document> findPublicDocuments() {
		return documentRepository.findAll();
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, '"+AppPermissionTypes.OWNER+"')")
	@Override
	public List<Document> findMyDocuments() {
		return documentRepository.findAll();
	}

	@Override
	public List<DocumentSampleDTO> convertToSampleDTOs(List<Document> documents) {
		return documents
				.stream()
				.map(document -> DocumentSampleDTO.builder()
						.id(document.getId())
						.name(document.getName())
						.ownerUsername(document.getOwnerUsername())
						.confidentiality(document.getConfidentiality())
						.build())
				.collect(Collectors.toList());
	}
}
