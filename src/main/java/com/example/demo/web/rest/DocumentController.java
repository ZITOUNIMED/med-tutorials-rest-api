package com.example.demo.web.rest;

import java.util.ArrayList;
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
import com.example.demo.entity.DocumentCollection;
import com.example.demo.service.DocumentService;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

	@Autowired
	private DocumentService documentService;

	@GetMapping({"", "/all"})
	public ResponseEntity<List<Document>> findAll(){
		return ResponseEntity.ok(documentService.findAll());
	}
	
	@GetMapping("/publicDocuments")
	public ResponseEntity<List<Document>> findPublicDocuments(){
		return ResponseEntity.ok(documentService.findPublicDocuments());
	}
	
	@GetMapping("/myFavoriteDocuments")
	public ResponseEntity<List<Document>> findMyFavoriteDocuments(){
		List<DocumentCollection> colelction = documentService.findMyFavoriteDocuments();
		if(colelction != null && colelction.size()>0){
			return ResponseEntity.ok(colelction.get(0).getDocuments());
		}
		return ResponseEntity.ok(new ArrayList<Document>());
	}
	
	@GetMapping("/myDocuments")
	public ResponseEntity<List<Document>> findMyDocuments(){
		return ResponseEntity.ok(documentService.findMyDocuments());
	}

	@PostMapping
	public ResponseEntity<Void> save(@RequestBody Document document){
		this.documentService.save(document);
		return ResponseEntity.accepted().build();
	}
	
	@PostMapping("/all")
	public ResponseEntity<Void> saveAll(@RequestBody List<Document> documents){
		this.documentService.saveAll(documents);
		return ResponseEntity.accepted().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Document> getDocument(@PathVariable Long id){
		return ResponseEntity.ok(documentService.findById(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDocument(@PathVariable Long id){
		documentService.deleteById(id);
		return ResponseEntity.accepted().build();
	}
	
	@GetMapping("/samples")
	public ResponseEntity<List<DocumentSampleDTO>>  getDocumentSamples() {
		List<DocumentCollection> colelction = documentService.findMyFavoriteDocuments();
		if(colelction != null && colelction.size()>0){
			return ResponseEntity.ok(documentService.convertToSampleDTOs(colelction.get(0).getDocuments()));
		}
		return ResponseEntity.ok(new ArrayList<>());
	}

}
