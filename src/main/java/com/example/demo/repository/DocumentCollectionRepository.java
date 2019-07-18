package com.example.demo.repository;

import com.example.demo.entity.DocumentCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentCollectionRepository extends JpaRepository<DocumentCollection, Long>{
//	List<DocumentCollection> findByOwnerUserName(String ownerUsername);
}
