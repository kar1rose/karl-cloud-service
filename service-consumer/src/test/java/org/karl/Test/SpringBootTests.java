package org.karl.Test;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author KARL ROSE
 * @date 2020/8/19 17:29
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringBootTests {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootTests.class);

    /**
     * 使用RocketMq的生产者
     */
    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @Test
    public void send() throws MQClientException, RemotingException, MQBrokerException, InterruptedException {

    }

}
