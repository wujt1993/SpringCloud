package com.jt.controller.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.util.ObjectMapperUtil;

@RestController
public class JSONPController {

	@RequestMapping("/web/testJSONP")
	public JSONPObject testJSONP(String callback) {
		User user = new User();
		user.setId(1);
		user.setName("kinth");
		JSONPObject jsonp = new JSONPObject(callback, user);
		return jsonp;
	}
	
	@RequestMapping("/web/testJSONP1")
	public String testJSONP1(String callback) {
		User user = new User();
		user.setId(1);
		user.setName("kinth");
		String json = ObjectMapperUtil.toJSON(user);
		return callback+"(" + json + ")";
	}
	
	class User{
		private Integer id;
		private String name;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		@Override
		public String toString() {
			return "User [id=" + id + ", name=" + name + "]";
		}
		
	}
}
