package com.xcy.rocketmq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Created by xuchunyang on 2018/9/6 11点00分
 */
//@SpringBootConfiguration
public class ConsumerConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ConsumerConfiguration.class);

    @Value("${rocketmq.consumer.groupName}")
    private String groupName;

    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.consumer.topic}")
    private String topic;

    @Value("${rocketmq.consumer.tag}")
    private String tag;

    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;

    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;


    @Autowired
    private MessageListener messageListener;


    @Bean
    public DefaultMQPushConsumer getPushConsumer() throws RocketMQException {

        if (StringUtils.isBlank(groupName)){
            throw new RocketMQException("groupName is null !!!");
        }
        if (StringUtils.isBlank(namesrvAddr)){
            throw new RocketMQException("namesrvAddr is null !!!");
        }
        if (StringUtils.isBlank(topic)){
            throw new RocketMQException("topic is null !!!");
        }
        if (StringUtils.isBlank(tag)){
            throw new RocketMQException("tag is null !!!");
        }


        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.setMessageListener(messageListener);

        try {
            consumer.subscribe(topic, tag);
            consumer.start();
            log.info("Consumer start successfully!!!" + "  groupName : " + groupName + "  namesrvAddr : " + namesrvAddr + "  topic : " + topic + "  tag : " + tag);
        } catch (MQClientException e) {
            log.error("Consumer start failed", e.getErrorMessage(), e);
            throw new RocketMQException(e);
        }

        return consumer;

    }


}
