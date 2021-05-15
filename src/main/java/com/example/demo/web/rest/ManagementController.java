package com.example.demo.web.rest;

import java.util.List;

import com.example.demo.entity.Document;
import com.example.demo.service.DocumentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/management")
public class ManagementController {
    private final DocumentService documentService;

    public ManagementController(DocumentService documentService){
        super();
        this.documentService = documentService;
    }

    @GetMapping("/export-documents-as-json")
    public ResponseEntity<List<Document>> exportDocumentsAsJson(){
		return ResponseEntity.ok(documentService.findAll());
	}
}
