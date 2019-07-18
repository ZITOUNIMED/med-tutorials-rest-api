package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.DocumentSampleDTO;
import com.example.demo.entity.Document;
import com.example.demo.entity.DocumentCollection;

public interface DocumentService extends CrudService<Document, Long>{
	List<Document> findMyDocuments();
	
	List<DocumentCollection> findMyFavoriteDocuments();

	List<Document> findPublicDocuments();

	List<DocumentSampleDTO>  convertToSampleDTOs(List<Document> documents);
}
