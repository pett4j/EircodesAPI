package com.pett4j.api.filters;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletResponseWrapper;

public class CachingPostFilter extends CachingBaseFilter {

	public CachingPostFilter(CacheManager cacheManager) {
		super(cacheManager);
	}

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 900;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		return super.shouldFilter() && !ctx.getBoolean(CACHE_HIT);
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest req = ctx.getRequest();
		HttpServletResponse res = ctx.getResponse();

		if (isSuccess(res)) {
			Cache cache = cache(ctx);
			if (cache != null) {
				String key = cacheKey(req);
				cache.put(key,  res);
				log.debug("Cached successful response for '{}' into '{}' cache", key, cache.getName());
			}
		}
		return null;
	}

	private boolean isSuccess(HttpServletResponse res) {
		return (res != null) && (res.getStatus() < 300);
	}

}