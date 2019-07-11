package com.example.demo.service;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Attachment;
import com.example.demo.entity.Document;
import com.example.demo.entity.Element;

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
		deleteUnAttachedAttachment(document);
		super.save(document);
		document.getElements()
		 .stream()
		 .forEach(elt -> {
			 if(elt.getAttachment() != null){
				 attachmentService.save(elt.getAttachment());
			 }
		 });
	}
	
	@Override
	public void deleteById(Long id){
		Document document = findById(id);
		document.getElements()
		.stream()
		.forEach(elt -> {
			if(elt.getAttachmentId()!=null){
				attachmentService.deleteById(elt.getAttachmentId());
			}
		});
		super.deleteById(id);
	}
	
	private void deleteUnAttachedAttachment(Document document){
		if(document.getId() != null){
			Document beforeUpdating = findById(document.getId());
			beforeUpdating.getElements()
			.stream()
			.filter(elt -> elt.getId() != null && !stillExisting(elt.getId(), document.getElements()))
			.forEach(elt -> {
				if(elt.getAttachmentId()!=null){
					attachmentService.deleteById(elt.getAttachmentId());
				}
			});
		}
	}

	private boolean stillExisting(Long id, List<Element> elements) {
		return elements.stream()
				.anyMatch(elt -> id.equals(elt.getId()));
	}
}
