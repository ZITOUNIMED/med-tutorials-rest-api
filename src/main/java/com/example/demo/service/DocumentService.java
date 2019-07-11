package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.DocumentSampleDTO;
import com.example.demo.entity.Document;

public interface DocumentService extends CrudService<Document, Long>{
	public List<DocumentSampleDTO> findAllDocumentSampleDTOs();
}
