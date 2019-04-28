package com.example.demo.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID")
	private User owner;
	
	@OneToMany(
			fetch= FetchType.EAGER, 
			cascade= CascadeType.ALL,
			orphanRemoval=true
	)
	private List<Element> elements;
	
}
