package org.karl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.karl.sh.warehouse.config.database.RedisConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author KARL ROSE
 * @date 2020/8/19 17:29
 **/
@SpringBootTest(classes = {RedisConfiguration.class})
@RunWith(SpringRunner.class)
public class SpringBootTests {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootTests.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void context() {
        ValueOperations<String, String> vo = stringRedisTemplate.opsForValue();
//        HashOperations<String, String, Object> ho = redisTemplate.opsForHash();
//        logger.info("user:{}", ho.get("USER", "0095BB5AF7B7"));
        vo.set("test", "rose");
        logger.info(vo.get("abc"));
        logger.info(vo.get("test"));
        for (int i = 0; i < 32; i++) {
            logger.info("{}={}", i, vo.getBit("test", i));
        }
    }


}
