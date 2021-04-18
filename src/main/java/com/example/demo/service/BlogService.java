package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.po.Blog;
import com.example.demo.vo.BlogQuery;

public interface BlogService {

	Blog getBlog(Long id);

	Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery);

	Blog saveBlog(Blog blog);

	Blog updateBlog(Long id, Blog blog);

	void deleteBlog(Long id);
}
