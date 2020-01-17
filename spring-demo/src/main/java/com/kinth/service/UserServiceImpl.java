package com.kinth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kinth.dao.UserMapper;
import com.kinth.pojo.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userMapper.findAll();
	}

	@Override
	public List<User> findUserByName(String name) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.like("name", name);
		return userMapper.selectList(queryWrapper);
	}

}
