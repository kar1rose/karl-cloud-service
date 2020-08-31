package org.karl.sh.provider.mapper;

import org.karl.sh.provider.model.SysUser;

/**
 * @author KARL ROSE
 * @date 2020/8/12 15:28
 **/
public interface SysUserMapper extends BaseMapper<SysUser, Long> {

    SysUser selectByUsername(String username);

}
