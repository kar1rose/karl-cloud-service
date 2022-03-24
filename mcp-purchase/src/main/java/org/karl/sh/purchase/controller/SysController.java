package org.karl.sh.purchase.controller;
/**
 * Created by KARL_ROSE on 2020/2/29 10:02
 */

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.karl.sh.purchase.client.SysService;
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
    private DefaultMQProducer defaultMqProducer;

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
        return defaultMqProducer.send(new Message(topic, tag, msg.getBytes()));
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
