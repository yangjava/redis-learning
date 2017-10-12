package com.jedis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class HelloWorld {

	private Jedis jedis;

	@Before
	public void setup() {
		jedis = new Jedis("127.0.0.1", 6379);
		// 权限认证
		// jedis.auth("admin");
		System.out.println("init jedis" + jedis);
		// 执行结果： init jedisredis.clients.jedis.Jedis@31932839
	}

	/**
	 * redis存储字符串
	 */
	@Test
	public void testString() {
		// 插入
		jedis.set("key", "helloworld");
		System.out.println("jedis set " + jedis.get("key"));
		// 执行结果：jedis set helloworld

		// setnx set not exits 如果存在,返回0
		Long setnx = jedis.setnx("key", "helloworld");
		System.out.println("jedis setnx " + setnx);
		// 执行结果： jedis setnx 0

		// 添加
		jedis.append("key", " !");
		System.out.println("jedis set " + jedis.get("key"));
		// 执行结果：jedis set helloworld !

		// 删除
		jedis.del("key");

		// 插入多个值
		String mset = jedis.mset("name", "yang", "age", "18", "QQ",
				"1280025885");
		System.out.println(mset);
		// /执行结果： OK
		// 自增
		jedis.incr("age");
		// 如果不是 数字,抛出异常
		// jedis.incr("name");
		System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-"
				+ jedis.get("QQ"));
		// 执行结果：yang-19-1280025885
	}

	/**
	 * redis 操作Map
	 */
	@Test
	public void testMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "wang");
		map.put("age", "20");
		map.put("QQ", "1280025885");
		jedis.hmset("user", map);

		// 取出user，注意结果是一个泛型的List
		List<String> rsmap = jedis.hmget("user", "name", "age", "QQ");
		// 执行结果：[wang, 20, 1280025885]
		System.out.println(rsmap);

		jedis.hdel("user", "age");
		System.out.println(jedis.hmget("user", "age")); // 因为删除了，所以返回的是 [null]
		System.out.println(jedis.hlen("user")); // 返回key为user的键中存放的值的个数2
		System.out.println(jedis.exists("user"));// 是否存在key为user的记录 返回true
		System.out.println(jedis.hkeys("user"));// 返回map对象中的所有key [QQ, name]
		System.out.println(jedis.hvals("user"));// 返回map对象中的所有value [1280025885,
												// wang]

		Iterator<String> iter = jedis.hkeys("user").iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			System.out.println(key + ":" + jedis.hmget("user", key));
			// QQ:[1280025885]
			// name:[wang]
		}
	}
	
	
	
    /** 
     * jedis操作List 
     */  
    @Test  
    public void testList(){  
        //开始前，先移除所有的内容  
        jedis.del("java framework");  
        System.out.println(jedis.lrange("java framework",0,-1));  
        //先向key java framework中存放三条数据  
        jedis.lpush("java framework","spring");  
        jedis.lpush("java framework","struts");  
        jedis.lpush("java framework","hibernate");  
        //再取出所有数据jedis.lrange是按范围取出，  
        // 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有  
        System.out.println(jedis.lrange("java framework",0,-1));  
        
        jedis.del("java framework");
        jedis.rpush("java framework","spring");  
        jedis.rpush("java framework","struts");  
        jedis.rpush("java framework","hibernate"); 
        System.out.println(jedis.lrange("java framework",0,-1));
    } 
	
	
    /** 
     * jedis操作Set 
     */  
    @Test  
    public void testSet(){  
        //添加  
        jedis.sadd("user","liuling");  
        jedis.sadd("user","xinxin");  
        jedis.sadd("user","ling");  
        jedis.sadd("user","zhangxinxin");
        jedis.sadd("user","who");  
        //移除noname  
        jedis.srem("user","who");  
        System.out.println(jedis.smembers("user"));//获取所有加入的value  
        System.out.println(jedis.sismember("user", "who"));//判断 who 是否是user集合的元素  
        System.out.println(jedis.srandmember("user"));  
        System.out.println(jedis.scard("user"));//返回集合的元素个数  
    }  
  
    @Test  
    public void test() throws InterruptedException {  
        //jedis 排序  
        //注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）  
        jedis.del("a");//先清除数据，再加入数据进行测试  
        jedis.rpush("a", "1");  
        jedis.lpush("a","6");  
        jedis.lpush("a","3");  
        jedis.lpush("a","9");  
        System.out.println(jedis.lrange("a",0,-1));// [9, 3, 6, 1]  
        System.out.println(jedis.sort("a")); //[1, 3, 6, 9]  //输入排序后结果  
        System.out.println(jedis.lrange("a",0,-1));  
    }  
    
    @Test
    public void testRedisPool() {
        RedisUtil.getJedis().set("newname", "中文测试");
        System.out.println(RedisUtil.getJedis().get("newname"));
    }
	
	
	
	
	
	
}
