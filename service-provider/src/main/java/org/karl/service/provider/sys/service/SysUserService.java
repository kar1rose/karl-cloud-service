package org.karl.service.provider.sys.service;

import org.karl.service.provider.sys.mapper.SysUserMapper;
import org.karl.service.provider.sys.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(key = "#username")
    public SysUser getByUsername(String username) {
        return sysUserMapper.selectByUsername(username);
    }


}
