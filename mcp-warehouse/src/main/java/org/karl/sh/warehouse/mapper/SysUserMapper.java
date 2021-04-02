package org.karl.sh.warehouse.mapper;

import org.karl.sh.warehouse.model.PurchaseUser;

/**
 * @author KARL ROSE
 * @date 2020/8/12 15:28
 **/
public interface SysUserMapper extends BaseMapper<PurchaseUser, Long> {

    PurchaseUser selectByUsername(String username);

}
