package org.karl.sh.warehouse.controller;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.karl.sh.warehouse.model.SysUser;
import org.karl.sh.warehouse.service.SysUserService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SysController
 *
 * @author ROSE
 * @date 2020/2/29 09:54
 **/
@RestController
@RequestMapping("/")
public class SysController {

    private static final Logger logger = LogManager.getLogger(SysController.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private SysUserService sysUserService;

    private static final Integer CORE_POOL_SIZE = 10;
    private static final Integer MAX_POOL_SIZE = 20;
    private static final Long KEEP_ALIVE_TIME = 200L;
    private static final String LOCK_KEY = "LOCK";
    private static final String SYS_USER_KEY = "USER";

    @GetMapping("sys/say")
    public String say(@RequestParam("name") String name,
                      @RequestParam(value = "startDate", defaultValue = "2020-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate) {
        return DateFormatUtils.format(startDate, "yyyy-MM-dd") + "Hello " + name;
    }

    @GetMapping("user/{username}")
    public SysUser user(@PathVariable String username) {
        SysUser sysUser = sysUserService.getByUsername(username);
        if (sysUser != null) {
            redisTemplate.opsForHash().put(SYS_USER_KEY, sysUser.getUserId(), sysUser);
        }
        return sysUser;
    }

    @PostMapping("user")
    public String save() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), threadFactory);
        int THREAD_COUNT_SIZE = 100;
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT_SIZE);
        AtomicInteger count = new AtomicInteger();
        RLock lock = redissonClient.getLock(LOCK_KEY);
        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                try {
                    lock.lock();
                    int id = count.incrementAndGet();
                    logger.debug("锁定成功:{}", id);
                    //Thread.sleep(1500);
                    SysUser sysUser = new SysUser(String.valueOf(id), UUID.randomUUID().toString(), "rose" + Math.random(), "password");
                    redisTemplate.opsForHash().put(SYS_USER_KEY, String.valueOf(id), sysUser);
                    lock.unlock();
                    logger.debug("解锁完成:{}", id);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }
        try {
            // 一定记得加上timeout时间，防止阻塞主线程
            latch.await(15, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        //4.等待所有子任务完成
        if (count.get() != THREAD_COUNT_SIZE) {
            return "fail";
        }
        //5.关闭线程池
        executorService.shutdown();
        return "success";
    }


}
