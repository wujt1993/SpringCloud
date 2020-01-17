package com.kinth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kinth.pojo.User;
import com.kinth.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("findAll")
	public List<User> findAll() {
		return userService.findAll();
	}
	
	@RequestMapping("/findUserByName")
	public List<User> findUserByName(String name) {
		return userService.findUserByName(name);
	}
}
