package com.kazge.example.api;

import com.kazge.example.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class T1RemoteTest {

	private RestTemplate restTemplate;
	
	@Before
	public void setup() {
		restTemplate = new RestTemplate();
	}

	@Test
	public void getAllTest() {
		
		final String baseUrl = "http://localhost:8080";
		User user = new User();
		user.setName("test user");
		String u = this.restTemplate.postForObject(baseUrl + "/hello/gusers", user, String.class);
		System.out.println(">>>>>>>>>>>>"+u);
		String body = this.restTemplate.getForObject(baseUrl + "/hello/gusers", String.class);
		System.out.println(">>>>>>>>>>>>"+body);
		//assertThat(body).isEqualTo("Hello World");
	}
}
