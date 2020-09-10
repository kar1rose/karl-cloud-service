package org.karl.sh.auth.config;

import org.karl.sh.auth.mapper.SysUserMapper;
import org.karl.sh.core.beans.sys.SysAuth;
import org.karl.sh.core.beans.sys.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KARL.ROSE
 * @date 2020/5/18 12:13 下午
 **/
@Component
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 重写security的查询方法
     *
     * @author KARL.ROSE
     * @date 2020/5/18 12:39 下午
     **/
    @Override
    public SysUser loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserMapper.selectByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("username not found:" + username);
        }
        List<SysAuth> authorities = new ArrayList<>();
        authorities.add(new SysAuth("20200202","customer","customer"));
        user.setAuthorities(authorities);
        return user;
    }
}
