package com.example.demo.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentCollection {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String ownerUsername;
	
	@NotNull
	private String type;
	
	@OneToMany(
//			fetch= FetchType.EAGER,
			orphanRemoval=false
	)
	private List<User> members;
	
	@OneToMany(
//			fetch= FetchType.EAGER, 
			orphanRemoval=false
	)
	private List<Document> documents;

}
