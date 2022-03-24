package org.karl.service.gateway.service.impl;

import org.karl.service.gateway.service.IRateLimitService;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 限速实现类
 *
 * @author KARL.ROSE
 * @date 2021/4/1 16:54
 */

@Service
public class RateLimitServiceImpl implements IRateLimitService {

    private static final Logger log = LoggerFactory.getLogger(RateLimitServiceImpl.class);

    private static final String REDIS_RATE_LIMIT_KEY_ROOT = "RATE_LIMIT_KEY:";

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public Boolean limited(String url, Integer limitCount) {
        String key = REDIS_RATE_LIMIT_KEY_ROOT + url;
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        rateLimiter.trySetRate(RateType.OVERALL, 10, 1, RateIntervalUnit.MINUTES);
        return rateLimiter.tryAcquire(1);
    }
}
