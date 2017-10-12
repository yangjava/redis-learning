package com.jedis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
/**
 * 需要配置stringRedisTemplate
 *   <bean id="stringRedisTemplate" class="org.springframework.data.redis.core.StringRedisTemplate">
 *   <property name="connectionFactory" ref="jedisConnectionFactory" />
 *   </bean>
 *   这里配置了RedisTemplate和StringRedisTemplate,
 *   不同之处在于StringRedisTemplate的Key-Value序列化使用的是StringRedisSerializer。使用StringRedisTemplate操作Redis之后的结果是读友好的。
 *  另外对Hash类型而言，还有对应的HashKey序列化（其对应于Hash类型的字段名）。
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:spring-base.xml"})
public class JedisLuaTest {
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	// 测试Lua脚本
	  @Test
	  public void test71() {
	    List<String> keys = new ArrayList<String>();
	    RedisScript<Long> script = new DefaultRedisScript<Long>(
	        "local size = redis.call('dbsize'); return size;", Long.class);
	    Long dbsize = stringRedisTemplate
	        .execute(script, keys, new Object[] {});
	    System.out.println("sha1:" + script.getSha1());
	    System.out.println("Lua:" + script.getScriptAsString());
	    System.out.println("dbsize:" + dbsize);
	  }

	  @Test
	  public void test72() {
	    DefaultRedisScript<Boolean> script = new DefaultRedisScript<Boolean>();
	    /**
	     * isexistskey.lua内容如下：
	     * 
	     * return tonumber(redis.call("exists",KEYS[1])) == 1;
	     */
	    script.setScriptSource(new ResourceScriptSource(new ClassPathResource(
	        "/isexistskey.lua")));

	    script.setResultType(Boolean.class);// Must Set

	    System.out.println("script:" + script.getScriptAsString());
	    Boolean isExist = stringRedisTemplate.execute(script,
	        Collections.singletonList("k2"), new Object[] {});
	    Assert.assertTrue(isExist);
	  }
}
