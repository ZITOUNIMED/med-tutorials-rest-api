package com.example.demo.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DocumentSampleDTO;
import com.example.demo.entity.Document;
import com.example.demo.service.DocumentService;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

	@Autowired
	private DocumentService documentService;

	@GetMapping
	public ResponseEntity<List<Document>> getDocuments(){
		return ResponseEntity.ok(documentService.getDocuments());
	}

	@PostMapping
	public ResponseEntity<Void> saveDocument(@RequestBody Document document){
		this.documentService.saveDocument(document);
		return ResponseEntity.accepted().build();
	}
	
	@PostMapping("/all")
	public ResponseEntity<Void> saveAllDocuments(@RequestBody List<Document> documents){
		this.documentService.saveAllDocuments(documents);
		return ResponseEntity.accepted().build();
	}


	@GetMapping("/{id}")
	public ResponseEntity<Document> getDocument(@PathVariable Long id){
		return ResponseEntity.ok(documentService.getDocument(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDocument(@PathVariable Long id){
		documentService.deleteDocument(id);
		return ResponseEntity.accepted().build();
	}
	
	@GetMapping("/samples")
	public ResponseEntity<List<DocumentSampleDTO>>  getDocumentSamples() {
		return ResponseEntity.ok(documentService.getDocumentSamples());
	}

}
