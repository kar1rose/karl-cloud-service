package org.karl.sh.purchase.model;

import lombok.Data;

import java.util.Date;

/**
 * @author KARL ROSE
 * @date 2020/8/25 14:18
 **/
@Data
public class PurchaseOrder {

    private String orderId;
    private Date datetime;
    private boolean state;
    private Integer userId;

}
