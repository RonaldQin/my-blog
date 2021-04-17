package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.po.Blog;

public interface BlogService {

	Blog getBlog(Long id);

	Page<Blog> listBlog(Pageable pageable, Blog blog);

	Blog saveBlog(Blog blog);

	Blog updateBlog(Long id, Blog blog);

	void deleteBlog(Long id);
}
