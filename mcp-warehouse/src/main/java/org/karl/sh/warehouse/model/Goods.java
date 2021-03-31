package org.karl.sh.warehouse.model;

/**
 * @author KARL ROSE
 * @date 2020/8/25 14:31
 **/
public class Goods {

    private Integer goodsId;
    private String goodsName;
    private Integer num;
    private Integer count;

    public Goods(){

    }

    public Goods(Integer goodsId, String goodsName, Integer num) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.num = num;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
