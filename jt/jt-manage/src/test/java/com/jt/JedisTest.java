package com.jt;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.Item;

import redis.clients.jedis.Jedis;

public class JedisTest {
	
	@Test
	public void test05() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		User user = new User();
		user.setAge(27);
		user.setName("wujt");
		String asString = mapper.writeValueAsString(user);
		System.out.println(asString);
		Jedis jedis = new Jedis("192.168.247.132", 6379);
		jedis.set("admin", asString);
		String admin = jedis.get("admin");
		User readValue = mapper.readValue(admin, User.class);
		System.out.println(readValue);
	}
	
	@Test
	public void test04() throws InterruptedException {
		Jedis jedis = new Jedis("192.168.247.132", 6379);
		jedis.setex("a", 2, "a");
		System.out.println("原始值："+jedis.get("a"));
		jedis.setnx("a", "b");
		System.out.println("修改失败："+jedis.get("a"));
		Thread.sleep(3000);
		jedis.setnx("a", "A");
		System.out.println("修改成功："+jedis.get("a"));
		
	}
	
	@Test
	public void test03() {
		Jedis jedis = new Jedis("192.168.247.132", 6379);
		jedis.lpush("consumer", "1", "2", "3", "4");
		List<String> list = jedis.lrange("consumer", 0, 10);
		System.out.println(list);
		String consumer = null;
		while((consumer = jedis.rpop("consumer")) != null) {
			System.out.println(consumer);
		}
	}
	
	@Test
	public void test02() {
		Jedis jedis = new Jedis("192.168.247.132", 6379);
		jedis.hset("user", "name", "wujt");
		jedis.hset("user", "age", "27");
		Map<String, String> map = jedis.hgetAll("user");
		System.out.println(map);
	}
	
	@Test
	public void test01() throws InterruptedException {
		Jedis jedis = new Jedis("192.168.247.132", 6379);
		jedis.set("class", "java");
		jedis.setex("teacher", 1, "tony");
		System.out.println(jedis.get("class"));
		System.out.println(jedis.get("teacher"));
		Thread.sleep(2000);
		System.out.println("----------"+jedis.get("teacher"));
		
	}
	
}

class User{
	private String name;
	private int age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + "]";
	}
	
}
