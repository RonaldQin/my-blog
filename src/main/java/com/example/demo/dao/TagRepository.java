package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.po.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
	Tag findByName(String name);
}
