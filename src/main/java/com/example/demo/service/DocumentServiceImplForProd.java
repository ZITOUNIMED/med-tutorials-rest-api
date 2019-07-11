package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Attachment;
import com.example.demo.entity.Document;

@Service
//@Profile("pre-prod", "prod")
@Primary
public class DocumentServiceImplForProd extends DocumentServiceImpl {
	 private AttachmentService attachmentService;
	 
	 public DocumentServiceImplForProd(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	@Override
	 public Document findById(Long id){
		 Document document = super.findById(id);
		 document.getElements()
		 .stream()
		 .forEach(elt -> {
			 if(elt.getAttachmentId() != null){
				 Attachment attachment = attachmentService.findById(elt.getAttachmentId());
				 elt.setAttachment(attachment);
			 }
		 });
		 return document;
	 }
	
	@Override
	public void save(Document document){
		super.save(document);
		document.getElements()
		 .stream()
		 .forEach(elt -> {
			 if(elt.getAttachment() != null){
				 attachmentService.save(elt.getAttachment());
			 }
		 });
	}
}
