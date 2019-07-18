package com.example.demo.config.security.permissions.services.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.example.demo.config.security.permissions.services.AppPermissionService;
import com.example.demo.entity.DocumentCollection;
import com.example.demo.util.AppDocumentPermissions;
import com.example.demo.util.DocumentCollectionTypes;

@Component
public class DocumentCollectionPermissionService implements AppPermissionService<DocumentCollection, String>{

	@Override
	public boolean hasPermission(Authentication authentication, DocumentCollection documentCollection, String permission) {
		User user = (User) authentication.getPrincipal();
		switch(permission){
			case AppDocumentPermissions.MY_FAVORITE:
				return (documentCollection.getOwnerUsername().equals(user.getUsername()) &&
						DocumentCollectionTypes.MY_FAVORITE_TUTOS.getName()
						.equals(documentCollection.getType()));
		}
		return false;
	}
	
}
