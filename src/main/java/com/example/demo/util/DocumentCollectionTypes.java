package com.example.demo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DocumentCollectionTypes {
	MY_TUTOS("MY_TUTOS"),
	PUBLIC_TUTOS("PUBLIC_TUTOS"),
	MY_FAVORITE_TUTOS("MY_FAVORITE_TUTOS");
	
	private final String name;
}
