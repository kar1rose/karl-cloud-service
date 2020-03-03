package org.karl.service.provider.sys.controller;
/**
 * Created by KARL_ROSE on 2020/2/29 9:54
 */

import org.springframework.beans.factory.annotation.Value;
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
public class SysController {

    @Value("${server.port}")
    String port;

    @GetMapping("/say")
    public String say(@RequestParam("name") String name) {
        return "Hello " + name + ", I'm from port " + port;
    }
}
