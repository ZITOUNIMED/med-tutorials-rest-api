package com.example.demo.web.rest;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Attachment;
import com.example.demo.service.AttachmentService;

@RestController
@RequestMapping("/api/upload")
public class UploadController {
	private final AttachmentService attachmentService;
	
	public UploadController(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	@PostMapping
	public ResponseEntity<Attachment> uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("width") int width,
			@RequestParam("height") int height){
		Attachment attachment = null;
		try {
			attachment = Attachment.builder()
					.data(file.getBytes())
					.width(width)
					.height(height)
					.name(file.getOriginalFilename())
			.build();
			attachmentService.save(attachment);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(attachment);
	}
	
	@GetMapping
	public ResponseEntity<List<Attachment>> findAll(){
		return ResponseEntity.ok(attachmentService.findAll());
	}
}
