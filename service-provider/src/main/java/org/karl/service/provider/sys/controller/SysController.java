package org.karl.service.provider.sys.controller;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.karl.service.provider.sys.model.SysUser;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/sys")
public class SysController {


    private static final Logger logger = LoggerFactory.getLogger(SysController.class);

    @Value("${server.port}")
    String port;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    private static final Integer CORE_POOL_SIZE = 10;
    private static final Integer MAX_POOL_SIZE = 20;
    private static final Long KEEP_ALIVE_TIME = 200L;
    private static final String LOCK_KEY = "LOCK";


    @GetMapping("/say")
    public String say(@RequestParam("name") String name,
                      @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate) {
        return DateFormatUtils.format(startDate, "yyyy-MM-dd") + "Hello " + name + ", I'm from port " + port;
    }

    @PostMapping("/user")
    public String index() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
                threadFactory);
        CountDownLatch latch = new CountDownLatch(10);
        AtomicInteger count = new AtomicInteger();
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                logger.info("抢占锁:{}", LOCK_KEY);
                RLock lock = redissonClient.getLock(LOCK_KEY);
                long id = System.currentTimeMillis();
                lock.lock();
                logger.info("锁定成功:{}", id);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SysUser sysUser = new SysUser(id, UUID.randomUUID().toString(), "password");
                redisTemplate.opsForHash().put("USER", String.valueOf(sysUser.getId()), sysUser);
                lock.unlock();
                logger.info("解锁完成:{}", id);
                count.incrementAndGet();
                latch.countDown();
            });
        }
        try {
            // 一定记得加上timeout时间，防止阻塞主线程
            latch.await(25, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        //4.等待所有子任务完成
        if (count.get() != 10) {
            return "fail";
        }
        //5.关闭线程池
        executorService.shutdown();
        return "success";
    }


}
