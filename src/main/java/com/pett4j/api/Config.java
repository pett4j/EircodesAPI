package com.pett4j.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.pett4j.api.filters.CachingPostFilter;
import com.pett4j.api.filters.CachingPreFilter;

@Configuration
@EnableCaching
@ComponentScan({ "com.pett4j.*" })
public class Config {

	public static final String EIRCODE_CACHE = "EIRCODE_CACHE";

	@Autowired
	private CacheManager cacheManager;

	@Bean
	public JedisConnectionFactory redisConnectionFactory() {
		JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();

		// Defaults
		redisConnectionFactory.setHostName("172.17.0.2");
		redisConnectionFactory.setPort(6379);
		return redisConnectionFactory;
	}

    @Bean
    public RedisTemplate<Object, Object> redisTemplate()
    {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setExposeConnection(true);
        
        return redisTemplate;
    }
    
    @Bean
    public RedisCacheManager cacheManager()
    {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate());
        redisCacheManager.setTransactionAware(true);
        redisCacheManager.setLoadRemoteCachesOnStartup(true);
        redisCacheManager.setUsePrefix(true);
        return redisCacheManager;
    }
	
	@Bean
	public CachingPreFilter cachingPreFilter() {
		return new CachingPreFilter(cacheManager);
	}

	@Bean
	public CachingPostFilter cachingPostFilter() {
		return new CachingPostFilter(cacheManager);
	}
}
