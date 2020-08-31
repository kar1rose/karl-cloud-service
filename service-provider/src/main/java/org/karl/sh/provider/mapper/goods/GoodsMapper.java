package org.karl.sh.provider.mapper.goods;

import org.karl.sh.provider.model.Goods;

/**
 * @author KARL ROSE
 * @date 2020/8/25 14:30
 **/
public interface GoodsMapper {

    int decrement(Integer goodsId,Integer count);

    Goods selectNumById(Integer goodsId);
}
