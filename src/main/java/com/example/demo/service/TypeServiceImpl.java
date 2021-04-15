package com.example.demo.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dao.TypeRepository;
import com.example.demo.exception.NotFoundException;
import com.example.demo.po.Type;
@Service
public class TypeServiceImpl implements TypeService {
	@Autowired
	private TypeRepository typeRepository;

	@Transactional
	@Override
	public Type saveType(Type type) {
		return typeRepository.save(type);
	}

	@Transactional
	@Override
	public Type getType(Long id) {
		Optional<Type> optional = typeRepository.findById(id);
		if (!optional.isPresent()) {
			throw new NotFoundException("不存在该类型。");
		}
		return optional.get();
	}
	
	@Transactional
	@Override
	public Type getTypeByName(String name) {
		return typeRepository.findByName(name);
	}

	@Transactional
	@Override
	public Page<Type> listType(Pageable pageable) {
		return typeRepository.findAll(pageable);
	}

	@Transactional
	@Override
	public Type updateType(Long id, Type type) {
		Optional<Type> optional = typeRepository.findById(id);
		if (!optional.isPresent()) {
			throw new NotFoundException("不存在该类型。");
		}
		Type t = optional.get();
		BeanUtils.copyProperties(type, t);
		return typeRepository.save(t);
	}

	@Override
	public void deleteType(Long id) {
		typeRepository.deleteById(id);
	}

}