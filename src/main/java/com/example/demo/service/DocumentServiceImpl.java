package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.demo.dto.DocumentSampleDTO;
import com.example.demo.entity.Document;
import com.example.demo.repository.DocumentRepository;

@Service
//@Profile({"default", "prod", "recipe"})
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	private DocumentRepository documentRepository;

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

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, 'read') or hasRole('ROLE_ADMIN')")
	@Override
	public List<Document> findAll() {
		return documentRepository.findAll();
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

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, 'read') or hasRole('ROLE_ADMIN')")
	@Override
	public List<DocumentSampleDTO> findAllDocumentSampleDTOs() {
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
}
