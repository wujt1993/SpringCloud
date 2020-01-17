package com.kinth;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kinth.pojo.Dog;
import com.kinth.pojo.User;

@SpringBootTest
@RunWith(Runner.class)
class SpringDemoApplicationTests {

	@Autowired
	private Dog dog;
	

	@Test
	public void testDog() {
		System.out.println(dog);
	}
}
