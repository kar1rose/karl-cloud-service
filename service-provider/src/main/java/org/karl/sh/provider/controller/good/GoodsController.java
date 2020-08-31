package org.karl.sh.provider.controller.good;

import org.karl.sh.core.templates.ApiResult;
import org.karl.sh.provider.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KARL ROSE
 * @date 2020/8/25 14:36
 **/
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @PutMapping("/{goodsId}/{count}")
    public ApiResult<String> decrement(@PathVariable Integer goodsId, @PathVariable Integer count) {
        try {
            goodsService.decrement(goodsId, count);
            return ApiResult.success();
        } catch (RuntimeException e) {
            return ApiResult.error(e.getMessage());
        }
    }
}
