package com.example.demo.config.security.permission;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import com.example.demo.dto.DocumentSampleDTO;
import com.example.demo.entity.Document;
import com.example.demo.util.ConfidentialityEnum;

public class DocumentPermissionService {

	public boolean hasPermission(Authentication authentication, Document document, String permission) {
		if("read".equals(permission)) {
			User user = (User) authentication.getPrincipal();
			if(user.getUsername().equals(document.getOwnerUsername()) ||
					ConfidentialityEnum.PUBLIC.getName().equals(document.getConfidentiality())) {
				return true;
			}
		}
		return false;
	}

	public boolean hasPermission(Authentication authentication, DocumentSampleDTO documentSampleDTO, String permission) {
		if("read".equals(permission)) {
			User user = (User) authentication.getPrincipal();
			if(user.getUsername().equals(documentSampleDTO.getOwnerUsername()) ||
					ConfidentialityEnum.PUBLIC.getName().equals(documentSampleDTO.getConfidentiality())) {
				return true;
			}
		}
		return false;
	}
	
}
