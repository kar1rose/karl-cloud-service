package org.karl.sh.purchase.controller.purchase;

import org.karl.sh.core.templates.ApiResult;
import org.karl.sh.purchase.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KARL ROSE
 * @date 2020/8/25 14:29
 **/
@RestController
@RequestMapping("/order")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping("/{userId}/{goodsId}/{num}")
    public ApiResult<String> order(@PathVariable Integer userId, @PathVariable Integer goodsId, @PathVariable Integer num) {
        try {
            orderService.createOrder(userId, goodsId, num);
            return ApiResult.success();
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

    @PostMapping("/{name}")
    public ApiResult<String> create(@PathVariable String name) {
        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            log.info("currentUser:{}", JsonUtils.convert(authentication));
            orderService.createOrder(name);
            return ApiResult.success();
        } catch (Exception e) {
            return ApiResult.error(e.getMessage());
        }
    }

}
