package com.jt;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

public class ShardedRedisTest {
	
	@Test
	public void test01() {
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		JedisShardInfo jedisShardInfo = new JedisShardInfo("192.168.247.132", 6379);
		JedisShardInfo jedisShardInfo1 = new JedisShardInfo("192.168.247.132", 6380);
		JedisShardInfo jedisShardInfo2 = new JedisShardInfo("192.168.247.132", 6381);
		shards.add(jedisShardInfo);
		shards.add(jedisShardInfo1);
		shards.add(jedisShardInfo2);
		ShardedJedis shardedJedis = new ShardedJedis(shards);
		shardedJedis.set("name", "wujt");
		System.out.println(shardedJedis.get("a"));
	}
}
