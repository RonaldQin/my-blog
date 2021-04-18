package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.dao.BlogRepository;
import com.example.demo.exception.NotFoundException;
import com.example.demo.po.Blog;
import com.example.demo.po.Type;
import com.example.demo.vo.BlogQuery;

@Service
public class BlogServiceImpl implements BlogService {
	@Autowired
	private BlogRepository blogRepository;

	@Override
	public Blog getBlog(Long id) {
		Optional<Blog> optional = blogRepository.findById(id);
		if (!optional.isPresent()) {
			throw new NotFoundException("不存在该博客。");
		}
		return optional.get();
	}

	@Override
	public Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery) {
		return blogRepository.findAll(new Specification<Blog>() {
			@Override
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (blogQuery.getTitle() != null && !"".equals(blogQuery.getTitle())) {
					predicates.add(criteriaBuilder.like(root.get("title"), "%" + blogQuery.getTitle() + "%"));
				}
				if (blogQuery.getTypeId() != null) {
					predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blogQuery.getTypeId()));
				}
				if (blogQuery.isRecommend()) {
					predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blogQuery.isRecommend()));
				}
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		}, pageable);
	}

	@Override
	public Blog saveBlog(Blog blog) {
		return blogRepository.save(blog);
	}

	@Override
	public Blog updateBlog(Long id, Blog blog) {
		Optional<Blog> optional = blogRepository.findById(id);
		if (!optional.isPresent()) {
			throw new NotFoundException("不存在该博客。");
		}
		Blog b = optional.get();
		BeanUtils.copyProperties(blog, b);
		return blogRepository.save(b);
	}

	@Override
	public void deleteBlog(Long id) {
		blogRepository.deleteById(id);
	}

}