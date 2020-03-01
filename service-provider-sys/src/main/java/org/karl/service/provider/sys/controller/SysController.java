package org.karl.service.provider.sys.controller;
/**
 * Created by KARL_ROSE on 2020/2/29 9:54
 */

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SysController
 * @Author ROSE
 * @Date 2020/2/29 09:54
 * @Description
 **/
@RestController
@RequestMapping("/sys")
@Slf4j
public class SysController {

    @GetMapping("/say")
    @SentinelResource(value = "say")
    public String say(@RequestParam("name") String name) {
        log.info("saying...");
        return "hello " + name;
    }
}
