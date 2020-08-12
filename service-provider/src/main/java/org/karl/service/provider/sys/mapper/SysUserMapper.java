package org.karl.service.provider.sys.mapper;

import org.karl.service.provider.sys.model.SysUser;

/**
 * @author KARL ROSE
 * @date 2020/8/12 15:28
 **/
public interface SysUserMapper extends BaseMapper<SysUser, Long> {

    SysUser selectByUsername(String username);

}
