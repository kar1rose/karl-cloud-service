package org.karl.sh.provider.controller.goods;

import org.karl.sh.core.constants.Auths;
import org.karl.sh.core.templates.ApiResult;
import org.karl.sh.provider.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ApiResult<Authentication> goods() {
        return ApiResult.success(SecurityContextHolder.getContext().getAuthentication());
    }
}
