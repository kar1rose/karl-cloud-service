package org.karl.sh.provider.annotations;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 防重复提交 aop
 *
 * @author ROSE
 * @date 2020/3/13 21:51
 **/
@Aspect
@Component
public class SubmitLockAspect {

    private static final Logger logger = LoggerFactory.getLogger(SubmitLockAspect.class);

    @Autowired
    private RedissonClient redissonClient;

    @Pointcut("@annotation(org.karl.sh.provider.annotations.SubmitLock)")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        SubmitLock submitLock = signature.getMethod().getAnnotation(SubmitLock.class);
        String lockKey = submitLock.key();
        RLock lock = redissonClient.getLock(lockKey);
        try {
            lock.lock();
            logger.info("key {} locked success:{}", lockKey, new Date());
            // 执行进程
            result = joinPoint.proceed();
        } finally {
            // 解锁
            lock.unlock();
            logger.info("key {} release success:{}", lockKey, new Date());
        }
        return result;
    }


}
