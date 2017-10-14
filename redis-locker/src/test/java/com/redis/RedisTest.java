package com.redis;

import redis.clients.jedis.Jedis;

public class RedisTest {
    public static void main(String str[]){
      Jedis jedis = new Jedis("127.0.0.1", 6379);
      String key="";
      RedisLock redisLock = new RedisLock(jedis,key, 10000, 20000);
      System.out.println("aaa");
  }
}
