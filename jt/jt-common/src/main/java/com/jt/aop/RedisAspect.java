package com.jt.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.anno.Cache_Find;
import com.jt.enu.KEY_ENUM;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.ShardedJedis;

@Service
@Aspect
public class RedisAspect {
	@Autowired(required = false)
	private ShardedJedis jedis;
	
	@Around("@annotation(cache_find)")
	public Object around(ProceedingJoinPoint joinPoint, Cache_Find cache_find) {
		String key = getKey(joinPoint,cache_find);
		String result = jedis.get(key);
		Object data = null;
		try {
			if(result == null) {
				data = joinPoint.proceed();
				String json = ObjectMapperUtil.toJSON(data);
				if(cache_find.secondes() == 0)
					//表示不要超时
					jedis.set(key, json);
				else
					jedis.setex(key, cache_find.secondes(), json);
				System.out.println("第一次查数据库");
			}else {
				Class targetClass = getClass(joinPoint);;
				data = ObjectMapperUtil.toObject(result, targetClass);
				System.out.println("缓存");
			}
		}catch(Throwable e) {
			e.printStackTrace();
		}
		return data;
	}

	
	private Class getClass(ProceedingJoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		return signature.getReturnType();
	}

	private String getKey(ProceedingJoinPoint joinPoint, Cache_Find cache_find) {
		KEY_ENUM key_ENUM = cache_find.keyType();
		if(key_ENUM.equals(key_ENUM.EMPTY)) {
			return cache_find.key();
		}
		String strArgs = String.valueOf(joinPoint.getArgs()[0]);
		String key =  cache_find.key()+"_"+strArgs;
		return key;
	}
}
