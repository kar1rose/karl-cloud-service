package org.karl.sh.warehouse.service;

import org.karl.sh.warehouse.annotations.SubmitLock;
import org.karl.sh.warehouse.mapper.goods.GoodsMapper;
import org.karl.sh.warehouse.model.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author KARL ROSE
 * @date 2020/8/25 14:41
 **/
@Service
public class GoodsService {

    @Autowired
    private GoodsMapper mapper;

    @SubmitLock(key = "lock:goods")
    @Transactional(rollbackFor = RuntimeException.class)
    public void decrement(Integer id, Integer count) {
        Goods goods = mapper.selectNumById(id);
        Assert.notNull(goods, "商品不存在");
        if (goods.getNum() < count) {
            throw new RuntimeException("商品数量不足");
        }
        if (mapper.decrement(id, count) != 1) {
            throw new RuntimeException("商品更新失败");
        }
    }
}
