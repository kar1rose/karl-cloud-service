package org.karl.sh.consumer.model;

import java.util.Date;

/**
 * @author KARL ROSE
 * @date 2020/8/25 14:18
 **/
public class PurchaseOrder {

    private String orderId;
    private Date datetime;
    private boolean state;
    private Integer userId;

    public PurchaseOrder(String orderId, Date datetime, boolean state, Integer userId) {
        this.orderId = orderId;
        this.datetime = datetime;
        this.state = state;
        this.userId = userId;
    }

    public PurchaseOrder() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
