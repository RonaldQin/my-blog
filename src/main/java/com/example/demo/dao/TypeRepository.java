package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.po.Type;

public interface TypeRepository extends JpaRepository<Type, Long> {
	Type findByName(String name);
}