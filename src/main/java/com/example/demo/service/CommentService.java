package com.example.demo.service;

import java.util.List;

import com.example.demo.po.Comment;

public interface CommentService {
	List<Comment> listCommentByBlogId(Long blogId);

	Comment saveComment(Comment comment);

}
