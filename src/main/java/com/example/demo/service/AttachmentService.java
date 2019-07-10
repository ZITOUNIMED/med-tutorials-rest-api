package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Attachment;
import com.example.demo.repository.AttachmentRepository;

@Service
public class AttachmentService {
	private final AttachmentRepository attachmentRepository;

	public AttachmentService(AttachmentRepository attachmentRepository) {
		super();
		this.attachmentRepository = attachmentRepository;
	}
	
	public void save(Attachment attachment){
		attachmentRepository.save(attachment);
	}
	
	public List<Attachment> findAll(){
		return attachmentRepository.findAll();
	}
 
}
