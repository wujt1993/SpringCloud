package com.jt.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

@Configuration
@PropertySource(value="classpath:/properties/redis.properties")
public class RedisConfig {
	
	
	@Value("${redis.nodes}")
	private String redisNodes; //node1,node2,node3
	
	@Bean
	public ShardedJedis shardedJedis() {
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		String[] nodes = redisNodes.split(",");
		for(String item : nodes) {
			JedisShardInfo info = new JedisShardInfo(item.split(":")[0], Integer.parseInt(item.split(":")[1]));
			shards.add(info);
		}
		ShardedJedis shardedJedis = new ShardedJedis(shards);
		return shardedJedis;
	}

//	@Value("${jedis.host}")
//	private String host;
//	@Value("${jedis.port}")
//	private int port;
//
//	@Bean
//	public Jedis jedis() {
//		return new Jedis(host, port);
//		
//	}
	
	
}
