package org.karl.sh.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author KARL ROSE
 * @date 2020/8/25 14:31
 **/
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Goods {

    private Integer goodsId;
    private String goodsName;
    private Integer num;
    private Integer count;


}
