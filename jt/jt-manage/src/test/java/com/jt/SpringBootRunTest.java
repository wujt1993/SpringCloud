package com.jt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import redis.clients.jedis.Jedis;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringBootRunTest {

	@Autowired
	private Jedis jedis;
	
	@Test
	public void test1() {
		String string = jedis.get("admin");
		System.out.println(string);
	}
}
