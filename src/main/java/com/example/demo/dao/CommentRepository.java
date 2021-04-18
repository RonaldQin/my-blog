package com.example.demo.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.po.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByBlogId(Long blogId, Sort sort);
}
