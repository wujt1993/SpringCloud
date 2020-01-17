package com.kinth.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kinth.pojo.User;

public interface UserMapper extends BaseMapper<User>{

	List<User> findAll();

}
