package com.example.demo.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="DOCUMENT_ID")
	private Long id;
	private String name;
	private String confidentiality;
	private String author;
	private String description;
	private LocalDate lastUpdateDate;
	private LocalDate creationDate;
	
	@NotNull
	private String ownerUsername;
	
	@OneToMany(
			fetch= FetchType.EAGER, 
			cascade= CascadeType.ALL,
			orphanRemoval=true
	)
	private List<Element> elements;
	
}
