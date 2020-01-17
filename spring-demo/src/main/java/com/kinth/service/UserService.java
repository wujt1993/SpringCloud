package com.kinth.service;

import java.util.List;

import com.kinth.pojo.User;

public interface UserService {

	List<User> findAll();

	List<User> findUserByName(String name);

}
