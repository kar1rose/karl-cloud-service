package org.karl.sh.warehouse.service;

import com.google.common.base.Preconditions;
import org.karl.sh.core.utils.BloomFilterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * @author KARL ROSE
 * @date 2021/1/26 15:36
 **/
@Component
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private ValueOperations<String, String> getValueOperations() {
        return redisTemplate.opsForValue();
    }

    /**
     * 根据给定的布隆过滤器添加值
     */
    public <T> void addByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        ValueOperations<String, String> vo = getValueOperations();
        for (int i : offset) {
            vo.setBit(key, i, true);
        }
    }

    /**
     * 根据给定的布隆过滤器判断值是否存在
     */
    public <T> boolean includeByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        ValueOperations<String, String> vo = getValueOperations();
        for (int i : offset) {
            Boolean bool = vo.getBit(key, i);
            if (bool == null || !bool) {
                return false;
            }
        }
        return true;
    }
}
