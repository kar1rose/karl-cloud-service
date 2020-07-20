package org.karl.service.provider.sys.controller;


import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * SysController
 *
 * @author ROSE
 * @date 2020/2/29 09:54
 **/
@RestController
@RequestMapping("/sys")
public class SysController {

    @Value("${server.port}")
    String port;

    @GetMapping("/say")
    public String say(@RequestParam("name") String name,
                      @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate) {
        return DateFormatUtils.format(startDate, "yyyy-MM-dd") + "Hello " + name + ", I'm from port " + port;
    }

}
