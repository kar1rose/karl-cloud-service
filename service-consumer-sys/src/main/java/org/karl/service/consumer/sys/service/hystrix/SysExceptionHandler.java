package org.karl.service.consumer.sys.service.hystrix;
/**
 * Created by KARL_ROSE on 2020/2/29 10:11
 */

import lombok.extern.slf4j.Slf4j;
import org.karl.service.consumer.sys.service.SysService;
import org.springframework.stereotype.Component;

/**
 * @ClassName SysExceptionHandler
 * @Author ROSE
 * @Date 2020/2/29 10:11
 * @Description
 **/
@Slf4j
@Component
public class SysExceptionHandler implements SysService {

    @Override
    public String say(String name) {
        log.error("消费降级{}", name);
        return "Sentinel {由于你的访问次数太多，已为你限流、您已进入保护模式，请稍后再试！}>>>熔断处理函数";
    }
}
