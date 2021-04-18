package com.example.demo.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CommentRepository;
import com.example.demo.po.Comment;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepository commentRepository;

	@Override
	public List<Comment> listCommentByBlogId(Long blogId) {
		Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
		return commentRepository.findByBlogId(blogId, sort);
	}

	@Transactional
	@Override
	public Comment saveComment(Comment comment) {
		Long parentCommentId = comment.getParentComment().getId();
		if (parentCommentId != -1) {
			comment.setParentComment(commentRepository.findById(parentCommentId).get());
		} else {
			comment.setParentComment(null);
		}
		comment.setCreateTime(new Date());
		return commentRepository.save(comment);
	}

}
