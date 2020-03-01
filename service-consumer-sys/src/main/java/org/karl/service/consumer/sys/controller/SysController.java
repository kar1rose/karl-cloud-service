package org.karl.service.consumer.sys.controller;
/**
 * Created by KARL_ROSE on 2020/2/29 10:02
 */

import org.karl.service.consumer.sys.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SysController
 * @Author ROSE
 * @Date 2020/2/29 10:02
 * @Description
 **/
@RequestMapping("/sys")
@RestController
public class SysController {

    @Autowired
    private SysService sysService;

//    @SentinelResource("hello")
    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable String name) {
        return sysService.say(name);
    }
}
