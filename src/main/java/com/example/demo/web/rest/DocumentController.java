package com.example.demo.web.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Document;
import com.example.demo.entity.AppCollection;
import com.example.demo.service.AppCollectionService;
import com.example.demo.service.DocumentService;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

	private final DocumentService documentService;
	private final AppCollectionService appCollectionService;

	public DocumentController(DocumentService documentService, AppCollectionService appCollectionService) {
		super();
		this.documentService = documentService;
		this.appCollectionService = appCollectionService;
	}

	@GetMapping({"", "/all"})
	public ResponseEntity<List<Document>> findAll(){
		return ResponseEntity.ok(documentService.findAll());
	}

	private Consumer<Document> makeLightDocument = (Document document) ->{
		document.setElements(null);
		document.setDescription("");
		document.setAuthor("");
		document.setOwnerUsername("");
		document.setLastUpdateDate(null);
	};

	@GetMapping("/myFavoriteDocuments")
	public ResponseEntity<List<Document>> findMyFavoriteDocuments(){
		return ResponseEntity.ok(new ArrayList<Document>());
	}
	
	@GetMapping("/myDocuments")
	public ResponseEntity<List<Document>> findMyDocuments(){
		return ResponseEntity.ok(documentService.findMyDocuments());
	}

	@GetMapping("/publicDocuments")
	public ResponseEntity<List<Document>> findPublicDocuments(){
		return ResponseEntity.ok(documentService.findPublicDocuments());
	}
	
	@GetMapping("/byCollectionId/{collectionId}")
	public ResponseEntity<Set<Document>> findDocumentsByCollectionId(@PathVariable Long collectionId){
		AppCollection appCollection = appCollectionService.findById(collectionId);
		if(appCollection == null){
			return ResponseEntity.ok(new HashSet<>());
		}
		return ResponseEntity.ok(appCollection.getDocuments());
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

	@GetMapping("/light/publicDocuments")
	public ResponseEntity<List<Document>> findLightPublicDocuments(){
		List<Document> documents = documentService.findPublicDocuments();
		return ResponseEntity.ok(documents.stream().peek(makeLightDocument)
				.collect(Collectors.toList()));
	}

	@GetMapping("/light/myDocuments")
	public ResponseEntity<List<Document>> findLightMyDocuments(){
		List<Document> documents = documentService.findMyDocuments();
		return ResponseEntity.ok(documents.stream().peek(makeLightDocument)
				.collect(Collectors.toList()));
	}

}
