package org.karl.redis.client;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author KARL ROSE
 * @date 2020/6/11 18:59
 **/
public class RedissionTest {


    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://139.224.83.117:6666");
        config.useSingleServer().setPassword("8925cfe04a8c4ff5a7eee2d1f79de59f");

        final RedissonClient client = Redisson.create(config);
        RLock lock = client.getLock("lock1");
        try{
            lock.lock();
        }finally{
            lock.unlock();
        }
    }
}
