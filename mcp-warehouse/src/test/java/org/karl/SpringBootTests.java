package org.karl;

import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.karl.sh.core.utils.BloomFilterHelper;
import org.karl.sh.warehouse.config.database.RedisConfiguration;
import org.karl.sh.warehouse.model.Goods;
import org.karl.sh.warehouse.service.RedisService;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author KARL ROSE
 * @date 2020/8/19 17:29
 **/
@SpringBootTest(classes = {RedisConfiguration.class, RedisService.class})
@RunWith(SpringRunner.class)
public class SpringBootTests {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootTests.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisService redisService;

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void testEvent() {
        /*ServletContext sc = ContextLoader.getCurrentWebApplicationContext().getServletContext();
        ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
        TestEvent event = new TestEvent("hello world", "msg");
        ac.publishEvent(event);*/
    }

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

    private static final String key = "goods";

    @Test
    public void redisTest() {
        long start = System.currentTimeMillis();
//        HashOperations<String, String, Goods> ho = redisTemplate.opsForHash();
        HashMap<String, Goods> map = new HashMap<>();
        RMap<String,Goods> rMap =  redissonClient.getMap(key);
        for (int i = 0; i <= 1000; i++) {
            String id = UUID.randomUUID().toString();
//            map.put(id, Goods.builder().goodsName(id).count(i).build());
            rMap.put(id, Goods.builder().goodsName(id).count(i).build());
        }

//        ho.putAll(key, map);
        long end = System.currentTimeMillis();
        logger.info("used:{}", end - start);

//        logger.info("{} rows query", ho.size(key));

    }

    BloomFilterHelper<String> myBloomFilterHelper = new BloomFilterHelper<>((Funnel<String>) (from, into) -> {
        into.putString(from, Charsets.UTF_8).putString(from, Charsets.UTF_8);
    }, 100, 0.01);

    @Test
    public void bloomFilter() {
        redisService.addByBloomFilter(myBloomFilterHelper, "user_bloom", "karlrose918");
    }

    @Test
    public void bloomFilterQuery() {
        boolean flag = redisService.includeByBloomFilter(myBloomFilterHelper, "user_bloom", "karlrose");
        logger.info("the id exists is {}", flag);
    }

    @Test
    public void testBucket() {
        long start = System.currentTimeMillis();
        /*for (int i = 0; i < 1000; i++) {
            // 33101
            redissonClient.getBucket("id" + i).set(String.valueOf(System.currentTimeMillis()));
            // 29754
            stringRedisTemplate.opsForValue().set("id" + i, String.valueOf(System.currentTimeMillis()));
        }*/
        for (Map.Entry<Object, Object> entrySet : redissonClient.getMap(key).entrySet()) {
            logger.info("key={},value={}", entrySet.getKey(), entrySet.getValue());
        }
        long end = System.currentTimeMillis();
        logger.info("used :{}", end - start);
    }


}
