package org.karl.sh.auth.mapper;

import org.karl.sh.core.beans.sys.SysUser;
import org.springframework.stereotype.Repository;

/**
 * @author KARL ROSE
 * @date 2020/9/8 14:43
 **/
public interface SysUserMapper {

    SysUser selectByUsername(String username);
}
