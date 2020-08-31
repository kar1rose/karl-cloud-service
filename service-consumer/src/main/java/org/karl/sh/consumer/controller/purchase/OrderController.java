package org.karl.sh.consumer.controller.purchase;

import org.karl.sh.consumer.service.OrderService;
import org.karl.sh.core.templates.ApiResult;
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

}
