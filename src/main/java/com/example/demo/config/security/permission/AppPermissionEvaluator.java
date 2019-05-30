package com.example.demo.config.security.permission;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.demo.dto.DocumentSampleDTO;
import com.example.demo.entity.Document;

@Component
public class AppPermissionEvaluator implements PermissionEvaluator{
	private DocumentPermissionService documentPermissionService;
	
	public AppPermissionEvaluator() {
		documentPermissionService = new DocumentPermissionService();
	}

	@Override
	public boolean hasPermission(Authentication authentication, Object targetObj, Object permission) {
		if(targetObj instanceof Document) {
			return documentPermissionService.hasPermission(authentication, (Document) targetObj, (String) permission);
		} else if(targetObj instanceof DocumentSampleDTO) {
			return documentPermissionService.hasPermission(authentication, (DocumentSampleDTO) targetObj, (String) permission);
		}
		
		return false;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		
		return false;
	}

}
