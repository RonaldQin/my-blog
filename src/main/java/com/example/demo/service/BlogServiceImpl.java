package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.dao.BlogRepository;
import com.example.demo.exception.NotFoundException;
import com.example.demo.po.Blog;
import com.example.demo.po.Type;
import com.example.demo.util.MarkdownUtils;
import com.example.demo.util.MyBeanUtils;
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
	
	@Transactional
	@Override
	public Blog getAndConvert(Long id) {
		Optional<Blog> optional = blogRepository.findById(id);
		if (!optional.isPresent()) {
			throw new NotFoundException("不存在该博客。");
		}
		Blog blog = optional.get();
		Blog b = new Blog();
		BeanUtils.copyProperties(blog, b);
		String content = b.getContent();
		b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
		blogRepository.updateViews(id);
		return b;
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
	public Page<Blog> listBlog(Pageable pageable) {
		return blogRepository.findAll(pageable);
	}
	
	@Override
	public Page<Blog> listBlog(String query, Pageable pageable) {
		return blogRepository.findByQuery(query, pageable);
	}
	
	@Override
	public Page<Blog> listBlog(Long tagId, Pageable pageable) {
		return blogRepository.findAll(new Specification<Blog>() {
			@Override
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Join join = root.join("tags");
				return criteriaBuilder.equal(join.get("id"), tagId);
			}
		}, pageable);
	}
	
	@Override
	public List<Blog> listRecommendBlogTop(Integer size) {
		Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
		Pageable pageable = PageRequest.of(0, size, sort);
		return blogRepository.findTop(pageable);
	}
	
	@Override
	public Map<String, List<Blog>> archiveBlog() {
		List<String> years = blogRepository.findGroupYear();
		Map<String, List<Blog>> map = new HashMap<>();
		for (String year : years) {
			map.put(year, blogRepository.findByYear(year));
		}
		return map;
	}
	
	@Override
	public Long countBlog() {
		return blogRepository.count();
	}

	@Transactional
	@Override
	public Blog saveBlog(Blog blog) {
		if (blog.getId() == null) {
			blog.setCreateTime(new Date());
			blog.setUpdateTime(new Date());
			blog.setViews(0);
		} else {
			blog.setUpdateTime(new Date());
		}
		return blogRepository.save(blog);
	}

	@Transactional
	@Override
	public Blog updateBlog(Long id, Blog blog) {
		Optional<Blog> optional = blogRepository.findById(id);
		if (!optional.isPresent()) {
			throw new NotFoundException("不存在该博客。");
		}
		Blog b = optional.get();
		BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
		b.setUpdateTime(new Date());
		return blogRepository.save(b);
	}

	@Transactional
	@Override
	public void deleteBlog(Long id) {
		blogRepository.deleteById(id);
	}

}
