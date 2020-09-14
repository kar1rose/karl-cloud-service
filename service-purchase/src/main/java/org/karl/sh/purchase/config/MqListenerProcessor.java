package org.karl.sh.purchase.config;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author KARL ROSE
 * @date 2020/8/19 17:26
 **/
@Configuration
public class MqListenerProcessor implements MessageListenerConcurrently {

    private static final Logger logger = LoggerFactory.getLogger(MqListenerProcessor.class);

    private static final String TOPIC = "KarlTopic";

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        if (CollectionUtils.isEmpty(msgs)) {
            logger.info("接收到的消息为空，不做任何处理");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        logger.info("messages:{}", msgs.size());
        for (MessageExt ext : msgs) {
            logger.info("received message :{}", new String(ext.getBody()));
            if (ext.getTopic().equals(TOPIC)) {
//                if (ext.getTags().equals("*")) {
                int reconsumeTimes = ext.getReconsumeTimes();
                if (reconsumeTimes == 3) {
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                //TODO 处理对应的业务逻辑
                logger.info("执行业务逻辑");
            }
        }
//        }

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
