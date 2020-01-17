package com.kinth.pojo;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value="classpath:/properties/dog.properties")
public class Dog {
	@Value("${dog.name}")
	private String name;
	@Value("${dog.id}")
	private Integer id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Dog [name=" + name + ", id=" + id + "]";
	}
	
}
