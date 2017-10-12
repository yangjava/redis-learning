package com.jedis;

import java.io.Serializable;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisTemplateTest {

	@SuppressWarnings("unchecked")
	@Test
    public void testRedisTemplate(){
		@SuppressWarnings("resource")
		ApplicationContext ac =  new ClassPathXmlApplicationContext("classpath:/spring-base.xml");
		RedisTemplate<Serializable, Serializable> redisTemplate = (RedisTemplate<Serializable, Serializable>) ac.getBean("redisTemplate");
		System.out.println(redisTemplate);
		
	}
	
	@Test
	public void testUserDao(){
			ApplicationContext ac =  new ClassPathXmlApplicationContext("classpath:/spring-base.xml");
	        UserDao userDAO = (UserDao)ac.getBean("userDao");
	        User user1 = new User();
	        user1.setId(1);
	        user1.setName("yang");
	        userDAO.saveUser(user1);
	        User user2 = userDAO.getUser(1);
	        System.out.println(user2.getName());
		}
	}
