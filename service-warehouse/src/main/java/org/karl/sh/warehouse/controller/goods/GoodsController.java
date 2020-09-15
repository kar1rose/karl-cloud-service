package org.karl.sh.warehouse.controller.goods;

import org.karl.sh.core.constants.Auths;
import org.karl.sh.core.templates.ApiResult;
import org.karl.sh.warehouse.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author KARL ROSE
 * @date 2020/8/25 14:36
 **/
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PreAuthorize("hasAnyAuthority('" + Auths.ROLE_CUSTOMER + "')")
    @PutMapping("/{goodsId}/{count}")
    public ApiResult<String> decrement(@PathVariable Integer goodsId, @PathVariable Integer count) {
        try {
            goodsService.decrement(goodsId, count);
            return ApiResult.success();
        } catch (RuntimeException e) {
            return ApiResult.error(e.getMessage());
        }
    }

    @GetMapping
    public ApiResult<Object> goods() {
        ValueOperations<String, Object> v = redisTemplate.opsForValue();
        Object vo =  v.get("karl-auth-token:auth:236dbee7-c077-4758-9e65-c2fba054e839");
        return ApiResult.success(vo);
    }
}
