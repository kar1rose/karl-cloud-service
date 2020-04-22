package org.karl.service.consumer.sys.controller;
/**
 * Created by KARL_ROSE on 2020/2/29 10:02
 */

import org.karl.service.consumer.sys.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *  SysController
 * @author ROSE
 * @date 2020/2/29 10:02
 *
 **/
@RestController
public class SysController {

    @Autowired
    private SysService sysService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/hello-feign/{name}")
    public ResponseEntity<String> hello(@PathVariable String name) {
        return new ResponseEntity<>(sysService.say(name), HttpStatus.BAD_GATEWAY);
    }

    @GetMapping("/hello/{name}")
    public String helloFeign(@PathVariable String name) {
        return restTemplate.getForObject("http://service-provider-sys/sys/say?name=" + name, String.class);
    }

}
