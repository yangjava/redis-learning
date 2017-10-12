package com.redis;

import redis.clients.jedis.ShardedJedis;

public interface RedisDataSource {
	/**
	 * 
	 * @return 获取redis资源
	 */
	public ShardedJedis getResource();
    /**
     * 释放redis资源,如果出现异常,将资源返回给pool
     * @param shardedJedis
     * @param broken
     */
	public void returnResource(ShardedJedis shardedJedis, boolean broken);
}
