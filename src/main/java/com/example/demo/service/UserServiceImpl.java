package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserRepository;
import com.example.demo.po.User;
import com.example.demo.util.MD5Utils;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User checkUser(String username, String password) {
		User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
		return user;
	}

}
