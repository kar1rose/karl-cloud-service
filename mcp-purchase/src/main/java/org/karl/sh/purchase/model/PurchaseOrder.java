package org.karl.sh.purchase.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author KARL ROSE
 * @date 2020/8/25 14:18
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("purchase_order")
public class PurchaseOrder{

    /**订单号*/
    @TableId(type = IdType.AUTO)
    private String orderId;
    /**下单时间*/
    private Date datetime;
    /**状态*/
    private boolean state;
    private Integer userId;

}
