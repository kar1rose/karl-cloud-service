package org.karl.sh.warehouse.mapper;

import org.karl.sh.warehouse.model.SysUser;

/**
 * @author KARL ROSE
 * @date 2020/8/12 15:28
 **/
public interface SysUserMapper extends BaseMapper<SysUser, Long> {

    SysUser selectByUsername(String username);

}
