package org.karl.sh.consumer.service;

import org.karl.sh.consumer.service.hystrix.SysServiceFallbackImpl;
import org.karl.sh.core.templates.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Created by KARL_ROSE on 2020/2/29 10:03
 */
@FeignClient(value = "service-provider-sys", fallback = SysServiceFallbackImpl.class)
public interface SysService {

    @GetMapping(value = "/sys/say")
    String say(@RequestParam("name") String name);

    @PutMapping(value = "/goods/{goodsId}/{num}")
    ApiResult<String> decrement(@PathVariable Integer goodsId, @PathVariable Integer num);

}

