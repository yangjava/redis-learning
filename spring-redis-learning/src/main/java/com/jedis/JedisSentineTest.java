package com.jedis;


import java.util.Collection;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
/**
 * Redis Sentinel监听主服务，再主服务发生故障时能够切换至从服务，
 * 将从服务升为主服务来保证故障恢复，
 * 使用该功能需要在JedisConnectionFactory设置RedisSentinelConfiguration属性，
 * 目前Jedis对Redis Sentinel提供支持。
 *
 */
public class JedisSentineTest {
	public JedisConnectionFactory jedisConnetionFactory;
	
	public JedisConnection jedisConnection;
	
	@Before
	public void setup(){
		@SuppressWarnings("resource")
		ApplicationContext ac =  new ClassPathXmlApplicationContext("classpath:/spring-base.xml");
		jedisConnetionFactory=(JedisConnectionFactory)ac.getBean("connectionFactory");
		jedisConnection=jedisConnetionFactory.getConnection();
	}
	  @After
	  public void setAfter() {
	    jedisConnection.close();
	  }
	  
	  @Test
	  public void test3() throws InterruptedException {
	    if (jedisConnetionFactory.getSentinelConnection().isOpen()) {
	      Collection<RedisServer> c = jedisConnetionFactory
	          .getSentinelConnection().masters();
	      System.out.println(c);
	      RedisNode rn = new RedisNode("localhost", 6380);
	      rn.setName("mymaster");
	      c = jedisConnetionFactory.getSentinelConnection().slaves(rn);
	      System.out.println(c);
	    }

	    for (int i = 0; i < 1000; i++) {
	      jedisConnection.set(new String("k" + i).getBytes(), new String("v"
	          + i).getBytes());
	      Thread.sleep(1000);
	    }
	    Set<byte[]> keys = jedisConnection.keys(new String("k*").getBytes());
	    Assert.assertEquals(1000, keys.size());
	  }
	  
}
