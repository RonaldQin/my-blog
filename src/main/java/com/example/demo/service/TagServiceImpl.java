package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dao.TagRepository;
import com.example.demo.exception.NotFoundException;
import com.example.demo.po.Tag;
@Service
public class TagServiceImpl implements TagService {
	@Autowired
	private TagRepository tagRepository;

	@Override
	public Tag saveTag(Tag tag) {
		return tagRepository.save(tag);
	}

	@Override
	public Tag getTag(Long id) {
		Optional<Tag> optional = tagRepository.findById(id);
		if (!optional.isPresent()) {
			throw new NotFoundException("不存在该标签。");
		}
		return optional.get();
	}

	@Override
	public Tag getTagByName(String name) {
		return tagRepository.findByName(name);
	}

	@Override
	public Page<Tag> listTag(Pageable pageable) {
		return tagRepository.findAll(pageable);
	}
	
	@Override
	public List<Tag> listTag() {
		return tagRepository.findAll();
	}
	
	@Override
	public List<Tag> listTag(String ids) {
		return tagRepository.findAllById(convertToList(ids));
	}
	
	private List<Long> convertToList(String ids) {
		List<Long> list = new ArrayList<>();
		if (null != ids && "".equals(ids)) {
			String[] idarray = ids.split(",");
			for (int i = 0; i < idarray.length; i++) {
				list.add(new Long(idarray[i]));
			}
		}
		return list;
	}

	@Override
	public Tag updateTag(Long id, Tag tag) {
		Optional<Tag> optional = tagRepository.findById(id);
		if (!optional.isPresent()) {
			throw new NotFoundException("不存在该标签。");
		}
		Tag t = optional.get();
		BeanUtils.copyProperties(tag, t);
		return tagRepository.save(t);
	}

	@Override
	public void deleteTag(Long id) {
		tagRepository.deleteById(id);
	}

}
