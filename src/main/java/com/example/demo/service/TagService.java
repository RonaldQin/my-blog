package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.po.Tag;

public interface TagService {
	Tag saveTag(Tag tag);
	Tag getTag(Long id);
	Tag getTagByName(String name);
	Page<Tag> listTag(Pageable pageable);
	Tag updateTag(Long id, Tag tag);
	void deleteTag(Long id);
}
