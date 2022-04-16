package org.karl.sh.purchase.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.karl.sh.purchase.model.PurchaseOrder;

/**
 * @author KARL ROSE
 * @date 2020/8/25 14:22
 **/
public interface PurchaseOrderMapper extends BaseMapper<PurchaseOrder> {

    Integer createOrder(PurchaseOrder order);

}
