package com.example.demo.service.impl;

import com.example.demo.entity.Document;
import com.example.demo.repository.DocumentRepository;
import com.example.demo.service.DocumentService;
import com.example.demo.util.AppPermissionTypes;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {
	private final DocumentRepository documentRepository;
//	private Comparator<Document> sortByViewCount = (d1, d2) -> d1.getViewCount() != null && d2.getViewCount() != null ? d2.getViewCount().compareTo(d1.getViewCount()) : 0;

	public DocumentServiceImpl(DocumentRepository documentRepository) {
		super();
		this.documentRepository = documentRepository;
	}

	@Override
	@PreAuthorize("hasRole('ROLE_USER')")
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
		return documentRepository.findAll()
				.parallelStream()
				.sorted(Comparator.comparingDouble(Document::getViewCount))
//				.reversed())
				.collect(Collectors.toList());
	}

	@PostFilter("hasPermission(filterObject, '"+AppPermissionTypes.PUBLIC+"')")
	@Override
	public List<Document> findPublicDocuments() {
		return documentRepository.findAll()
				.parallelStream()
				.sorted(Comparator.comparing(Document::getViewCount))
				.collect(Collectors.toList());
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostFilter("hasPermission(filterObject, '"+AppPermissionTypes.OWNER+"')")
	@Override
	public List<Document> findMyDocuments() {
		return documentRepository.findAll()
				.parallelStream()
				.sorted(Comparator.comparing(Document::getViewCount))
				.collect(Collectors.toList());
	}

	@Override
	public void delete(Document document) {
		// TODO
	}
}
