package com.pett4j.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(EIRCODE_CACHE);
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
