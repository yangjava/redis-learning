package com.redis.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.redis.RedisClient;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:spring-base.xml"})
public class RedisClientTest {
	
	@Autowired
	private RedisClient redisClient;
	@Test
	public void testRedisClient(){
		redisClient.set("name", "yang");
		System.out.println(redisClient.get("name"));
	}

}
