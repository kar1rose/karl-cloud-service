package org.karl.service.consumer.sys.service;

import org.karl.service.consumer.sys.service.hystrix.SysExceptionHandler;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by KARL_ROSE on 2020/2/29 10:03
 */
@FeignClient(value = "service-provider-sys", fallback = SysExceptionHandler.class)
public interface SysService {

    @RequestMapping(value = "/sys/say", method = RequestMethod.GET)
    String say(@RequestParam("name") String name);
}
