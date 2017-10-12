package com.jedis;

import java.io.Serializable;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 熟悉Spring的JdbcTemplate对象的话，应该大概能猜出来RedisTemplate的作用了，
 * RedisTemplate对象对RedisConnection进行了封装
 * 它提供了连接管理，序列化等功能，它对Redis的交互进行了更高层次的抽象。另外还提供了Redis操作命令的操作视图
 * 这极大的方便和简化了Redis的操作。
 * 
 * 
下表是具体的操作视图接口类介绍：

Key类型操作
ValueOperations	Redis String/Value 操作
ListOperations	Redis List 操作
SetOperations	Redis Set 操作
ZSetOperations	Redis Sort Set 操作
HashOperations	Redis Hash 操作
Value约束操作
BoundValueOperations	Redis String/Value key 约束
BoundListOperations	Redis List key 约束
BoundSetOperations	Redis Set key 约束
BoundZSetOperations	Redis Sort Set key 约束
BoundHashOperations	Redis Hash key 约束
在org.springframework.data.redis.core包中对表中的接口都提供了相应的默认实现。
 */
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
