package org.karl.sh.purchase.service.hystrix;
/**
 * Created by KARL_ROSE on 2020/2/29 10:11
 */


import org.karl.sh.core.templates.ApiResult;
import org.karl.sh.purchase.service.SysService;
import org.springframework.stereotype.Component;

/**
 * SysExceptionHandler
 *
 * @author ROSE
 * @date 2020/2/29 10:11
 **/

@Component
public class SysServiceFallbackImpl implements SysService {

    @Override
    public String say(String name) {
        return "hystrix 服务降级";
    }

    @Override
    public ApiResult<String> decrement(Integer goodsId, Integer num) {
        //TODO 回滚
        return ApiResult.error("服务调用失败");
    }
}
