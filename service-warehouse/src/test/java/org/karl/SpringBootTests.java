package org.karl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.karl.event.Listener;
import org.karl.event.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author KARL ROSE
 * @date 2020/8/19 17:29
 **/
@SpringBootTest(classes = {Listener.class, Publisher.class})
@RunWith(SpringRunner.class)
@EnableAsync
public class SpringBootTests {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootTests.class);

    /*@Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisService redisService;

    @Test
    public void testEvent() {
        ServletContext sc = ContextLoader.getCurrentWebApplicationContext().getServletContext();
        ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
        RegisterEvent event = new RegisterEvent("hello world", "msg");
        ac.publishEvent(event);
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

    private static final String key = "sysuser";

    @Test
    public void redisTest() {
        HashOperations<String, String, String> ho = stringRedisTemplate.opsForHash();
        HashMap<String, String> map = new HashMap<>();
        for (int i = 2; i <= 10000; i++) {
            map.put(i + "", UUID.randomUUID().toString());
        }
        ho.putAll(key, map);

        logger.info("{} rows query", ho.size(key));

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
    }*/

    @Autowired
    private Publisher publisher;

    @Test
    public void eventTest(){
        logger.info("开始啦");
        publisher.publish("发布消息～");
        logger.info("注册完成");

    }


}
