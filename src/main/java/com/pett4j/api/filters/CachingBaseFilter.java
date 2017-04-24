package com.pett4j.api.filters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.pett4j.api.Config;

public abstract class CachingBaseFilter extends ZuulFilter {
	protected Logger log = LoggerFactory.getLogger(getClass());

	public static final String CACHE_HIT = "cacheHit";

	protected final CacheManager cacheManager;


	public CachingBaseFilter(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest req = ctx.getRequest();

		if (!getVerb(req).equals("GET")) {
			return false;
		}

		return true;
	}

	protected String cacheKey(HttpServletRequest req) {
		return req.getRequestURI();
	}

	protected Cache cache(RequestContext ctx) {
		return cacheManager.getCache(Config.EIRCODE_CACHE);
	}


	protected String getVerb(HttpServletRequest request) {
		String method = request.getMethod();
		if (method == null) {
			return "GET";
		}
		return method;
	}
}
