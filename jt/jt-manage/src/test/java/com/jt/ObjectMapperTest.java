package com.jt;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
public class ObjectMapperTest {

	@Test
	public void test01() throws IOException {
		Dog dog = new Dog();
		ObjectMapper mapper = new ObjectMapper();
		String string = mapper.writeValueAsString(dog);
		System.out.println(string); 
		Dog dog2 = mapper.readValue(string, Dog.class);
		System.out.println(dog2);
	}
}

class Dog{
	private Integer id = 1;
	private String name = "tomcat";
	
	
//	public Integer getIds() {
//		return id;
//	}
	
	public Integer getId() {
		return id;
	}

//	public void setId(Integer id) {
//		this.id = id;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name + "_";
	}

	@Override
	public String toString() {
		return "Dog [id=" + id + ", name=" + name + "]";
	}
	
}