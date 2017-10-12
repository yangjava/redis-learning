package com.jedis;

import java.util.Iterator;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

public class JedisConnectionTest {

	public JedisConnectionFactory jedisConnetionFactory;

	public JedisConnection jedisConnection;

	@Before
	public void setup() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"classpath:/spring-base.xml");
		jedisConnetionFactory = (JedisConnectionFactory) ac
				.getBean("connectionFactory");
		jedisConnection = jedisConnetionFactory.getConnection();
	}

	@After
	public void setAfter() {
		jedisConnection.close();
	}

	@Test
	public void testset() {
		if (!jedisConnection.exists(new String("name").getBytes())) {
			jedisConnection.set(new String("name").getBytes(), new String(
					"yang").getBytes());
		}
		new String(jedisConnection.get(new String("name").getBytes()));
		System.out.println(new String(jedisConnection.get(new String("name")
				.getBytes())));
	}

	@Test
	public void testKey() {
		Set<byte[]> keys = jedisConnection.keys(new String("*").getBytes());
		for (Iterator<byte[]> iter = keys.iterator(); iter.hasNext();) {
			System.out.println(new String(iter.next()));
		}
	}
}
