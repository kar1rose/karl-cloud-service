package org.karl.sh.purchase.config;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * rocketMq消费者配置
 *
 * @author KARL ROSE
 * @date 2020/8/19 17:25
 **/
@Configuration
public class RocketConsumerConfiguration {

    public static final Logger logger = LoggerFactory.getLogger(RocketConsumerConfiguration.class);

    @Value("${rocketmq.consumer.nameServerAddr}")
    private String nameServerAddr;
    @Value("${rocketmq.consumer.groupName}")
    private String groupName;
    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;
    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;
    @Value("${rocketmq.consumer.topics}")
    private String topics;
    @Value("${rocketmq.consumer.consumeMessageBatchMaxSize}")
    private int consumeMessageBatchMaxSize;

    @Autowired
    private RocketMqDemoListener rocketMqDemoListener;

    @Bean
    public DefaultMQPushConsumer rocketMqConsumer() throws Exception {
        if (StringUtils.isEmpty(groupName) || StringUtils.isEmpty(nameServerAddr) || StringUtils.isEmpty(topics)) {
            logger.error("groupName:{} ,nameServerAddr:{},topics：{}", groupName, nameServerAddr, topics);
            throw new IllegalArgumentException();
        }
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(nameServerAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.registerMessageListener(rocketMqDemoListener);

        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        /**
         * 设置消费模型，集群还是广播，默认为集群
         * 集群消费模式下,相同Consumer Group的每个Consumer实例平均分摊消息。
         * 广播消费模式下，相同Consumer Group的每个Consumer实例都接收全量的消息。
         */
        consumer.setMessageModel(MessageModel.CLUSTERING);

        /**
         * 设置一次消费消息的条数，默认为1条
         */
        consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);

        try {
            /**
             * 设置该消费者订阅的主题和tag，如果是订阅该主题下的所有tag，
             * 则tag使用*；如果需要指定订阅该主题下的某些tag，则使用||分割，例如tag1||tag2||tag3
             */
        	/*String[] topicTagsArr = topics.split(";");
        	for (String topicTags : topicTagsArr) {
        		String[] topicTag = topicTags.split("~");
        		consumer.subscribe(topicTag[0],topicTag[1]);
			}*/
            consumer.subscribe(topics, "*");
            consumer.start();
            logger.info("consumer is start !!! groupName:{},topics:{},namesrvAddr:{}", groupName, topics, nameServerAddr);

        } catch (Exception e) {
            logger.error("consumer is start !!! groupName:{},topics:{},namesrvAddr:{}", groupName, topics, nameServerAddr, e);
            throw new Exception(e);
        }

        return consumer;
    }
}
