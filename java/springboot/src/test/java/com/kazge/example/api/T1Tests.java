package com.kazge.example.api;

import com.kazge.example.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class T1Tests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void exampleTest() {
		String body = this.restTemplate.getForObject("/hello/greeting", String.class);
		System.out.println(">>>>>>>>>>>>"+body);
		//assertThat(body).isEqualTo("Hello World");
	}

	@Test
	public void getAllTest() {
		User user = new User();
		user.setName("test user");
		String u = this.restTemplate.postForObject("/t1/users", user, String.class);
		System.out.println(">>>>>>>>>>>>"+u);
		
	}




}
