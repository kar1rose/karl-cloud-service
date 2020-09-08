package org.karl.sh.provider.service;

import org.karl.sh.provider.mapper.SysUserMapper;
import org.karl.sh.provider.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author KARL ROSE
 * @date 2020/8/12 15:28
 **/

@CacheConfig(cacheNames = {"sysUser"})
@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Cacheable(key = "#username")
    public SysUser getByUsername(String username) {
        return sysUserMapper.selectByUsername(username);
    }

    public void save(){

    }

}
