package org.karl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.karl.sh.core.beans.sys.SysUser;
import org.karl.sh.warehouse.config.database.DruidConfiguration;
import org.karl.sh.warehouse.config.database.RedisConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author KARL ROSE
 * @date 2020/8/19 17:29
 **/
@SpringBootTest(classes = {DruidConfiguration.class})
@RunWith(SpringRunner.class)
public class SpringBootTests {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootTests.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testEvent(){
        ServletContext sc = Objects.requireNonNull(ContextLoader.getCurrentWebApplicationContext()).getServletContext();
        assert sc != null;
        WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)WebApplicationContextUtils.getWebApplicationContext(sc);
        SysUser user = new SysUser();
        BeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClassName(user.getClass().getName());
        assert beanFactory != null;
        beanFactory.registerBeanDefinition(user.getClass().getName(), beanDefinition);
    }

    @Test
    public void context() {
        ValueOperations<String, String> vo = stringRedisTemplate.opsForValue();
        vo.set("test", "rose");
        logger.info(vo.get("abc"));
        logger.info(vo.get("test"));
        for (int i = 0; i < 32; i++) {
            logger.info("{}={}", i, vo.getBit("test", i));
        }
    }

    private static final String key = "redis_test";

    @Test
    public void redisTest() {
        ListOperations<String, String> lo = stringRedisTemplate.opsForList();
        List<String> list = new ArrayList<>();
        for (int i = 0; i <= 1000000; i++) {
            list.add("中文测试中文测试中文测试中文测试中文测试中文测试中文测试中文测试中文测试中文测试");
        }
        logger.info("{} rows appended ", lo.rightPushAll(key, list));

        logger.info("{} rows query", lo.size(key));

    }




}
