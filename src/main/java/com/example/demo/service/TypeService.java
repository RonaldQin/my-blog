package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.po.Type;

public interface TypeService {
	Type saveType(Type type);
	Type getType(Long id);
	Type getTypeByName(String name);
	Page<Type> listType(Pageable pageable);
	Type updateType(Long id, Type type);
	void deleteType(Long id);
}
