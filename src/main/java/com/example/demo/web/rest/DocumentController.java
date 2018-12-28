package com.example.demo.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Document;
import com.example.demo.service.DocumentService;

@RestController
@RequestMapping("/api/document")
@CrossOrigin("http://localhost:4200")
public class DocumentController {
	
	@Autowired
	private DocumentService documentService;
	
	
	@GetMapping
	public ResponseEntity<List<Document>> getDocuments(){
		return ResponseEntity.ok(documentService.getDocuments());
	}
	
	@PostMapping
	public ResponseEntity<Void> addDocument(@RequestBody Document document){
		if(document.getId() != null) {
			this.documentService.saveDocument(document);
		} else {
			documentService.addDocument(document);
		}
		return ResponseEntity.accepted().build();
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Document> getDocument(@PathVariable Long id){
		return ResponseEntity.ok(documentService.getDocument(id));
	}

}
