package org.karl.sh.purchase.controller;
/**
 * Created by KARL_ROSE on 2020/2/29 10:02
 */

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import org.karl.sh.purchase.service.SysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * SysController
 *
 * @author ROSE
 * @date 2020/2/29 10:02
 **/
@RestController
public class SysController {

    public static final Logger logger = LoggerFactory.getLogger(SysController.class);

    @Autowired
    private SysService sysService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @GetMapping("/hello-feign/{name}")
    public ResponseEntity<String> hello(@PathVariable String name) {
        return new ResponseEntity<>(sysService.say(name), HttpStatus.BAD_GATEWAY);
    }

    @GetMapping("/hello/{name}")
    public String helloFeign(@PathVariable String name) {
        return restTemplate.getForObject("http://service-provider-sys/sys/say?name=" + name, String.class);

    }

    @PostMapping("/send")
    public SendResult send(@RequestParam(name = "topic", defaultValue = "KarlTopic") String topic,
                           @RequestParam(name = "msg") String msg,
                           @RequestParam(name = "tag", defaultValue = "RandomTag") String tag) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        //默认3秒超时
        return defaultMQProducer.send(new Message(topic, tag, msg.getBytes()));
    }

    @GetMapping("/thread/{name}")
    public String thread(@PathVariable String name) {
        logger.info("当前线程:{}", Thread.currentThread().getName());
        try {
            Thread.sleep(1000 * 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello " + name;
    }

}
