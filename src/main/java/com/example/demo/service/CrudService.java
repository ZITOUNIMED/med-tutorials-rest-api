package com.example.demo.service;

import java.util.List;

public interface CrudService<T, ID> {
	void save(T t);
	List<T> findAll();
	T findById(ID id);
	void deleteById(ID id);
	void saveAll(List<T> list);
}
