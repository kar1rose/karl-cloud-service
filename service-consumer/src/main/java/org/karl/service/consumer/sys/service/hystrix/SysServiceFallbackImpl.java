package org.karl.service.consumer.sys.service.hystrix;
/**
 * Created by KARL_ROSE on 2020/2/29 10:11
 */


import org.karl.service.consumer.sys.service.SysService;
import org.springframework.stereotype.Component;

/**
 *  SysExceptionHandler
 * @author ROSE
 * @date 2020/2/29 10:11
 *
 **/

@Component
public class SysServiceFallbackImpl implements SysService {

    @Override
    public String say(String name) {
        return "hystrix 服务降级";
    }
}
