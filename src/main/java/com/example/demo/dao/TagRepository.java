package com.example.demo.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.po.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
	Tag findByName(String name);
	@Query("select t from Tag t")
	List<Tag> findTop(Pageable pageable);
}
