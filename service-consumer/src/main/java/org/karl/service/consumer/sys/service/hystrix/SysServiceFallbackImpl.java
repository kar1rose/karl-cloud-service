package org.karl.service.consumer.sys.service.hystrix;
/**
 * Created by KARL_ROSE on 2020/2/29 10:11
 */


import lombok.extern.slf4j.Slf4j;
import org.karl.service.consumer.sys.service.SysService;
import org.springframework.stereotype.Component;

/**
 *  SysExceptionHandler
 * @author ROSE
 * @date 2020/2/29 10:11
 *
 **/

@Slf4j
@Component
public class SysServiceFallbackImpl implements SysService {

    @Override
    public String say(String name) {
        log.error("消费降级{}", name);
        return "hystrix 服务降级";
    }
}
