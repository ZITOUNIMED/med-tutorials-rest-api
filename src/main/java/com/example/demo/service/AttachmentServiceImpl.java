package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Attachment;
import com.example.demo.repository.AttachmentRepository;

@Service
public class AttachmentServiceImpl implements AttachmentService {
	private final AttachmentRepository attachmentRepository;

	public AttachmentServiceImpl(AttachmentRepository attachmentRepository) {
		this.attachmentRepository = attachmentRepository;
	}

	@Override
	public void save(Attachment t) {
		attachmentRepository.save(t);
	}

	@Override
	public Attachment findById(Long id) {
		return attachmentRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteById(Long id) {
		attachmentRepository.deleteById(id);
	}

	@Override
	public List<Attachment> findAll() {
		return attachmentRepository.findAll();
	}

	@Override
	public void saveAll(List<Attachment> list) {
		attachmentRepository.saveAll(list);
	}
 
}
