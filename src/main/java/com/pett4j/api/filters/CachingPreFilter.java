package com.pett4j.api.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.netflix.zuul.context.RequestContext;


public class CachingPreFilter extends CachingBaseFilter {

    public CachingPreFilter(CacheManager cacheManager) {
        super(cacheManager);
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 15;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest req = ctx.getRequest();

        Cache cache = cache(ctx);
        if (cache != null) {
            String key = cacheKey(req);
            Cache.ValueWrapper valueWrapper = cache.get(key);
            if (valueWrapper != null) {
                // TODO cache should probably not store HttpServletResponse
                HttpServletResponse res = (HttpServletResponse) valueWrapper.get();
                if (res != null) {
                    log.debug("Filling response for '{}' from '{}' cache", key, cache.getName());
                    ctx.setResponse(res);
                    ctx.set(CACHE_HIT, true);
                    return res;
                }
            }
        }
        ctx.set(CACHE_HIT, false);
        return null;
    }
}