package com.xcy.rocketmq;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Created by xuchunyang on 2018/9/6 10点44分
 */
@SpringBootConfiguration
public class ProducerConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ProducerConfiguration.class);

    @Value("${rocketmq.producer.groupName}")
    private String groupName;

    @Value("${rocketmq.producer.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.producer.instanceName}")
    private String instanceName;

    @Value("${rocketmq.producer.maxMessageSize}")
    private int maxMessageSize;

    @Value("${rocketmq.producer.sendMessageTimeout}")
    private int sendMsgTimeout;


    @Bean
    public DefaultMQProducer getProducer() throws RocketMQException {

        if (StringUtils.isBlank(this.groupName)) {
            throw new RocketMQException("groupName is blank");
        }
        if (StringUtils.isBlank(this.namesrvAddr)) {
            throw new RocketMQException("nameServerAddr is blank");
        }
        if (StringUtils.isBlank(this.instanceName)){
            throw new RocketMQException("instanceName is blank");
        }

        final DefaultMQProducer producer = new DefaultMQProducer(groupName);
        producer.setProducerGroup(groupName);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setInstanceName(instanceName);
        producer.setMaxMessageSize(maxMessageSize);
        producer.setSendMsgTimeout(sendMsgTimeout);
        producer.setRetryTimesWhenSendFailed(3);

        try {
            producer.start();
            log.info("Producer start successfully!!!" + "  groupName : " + groupName + "  namesrvAddr : " + namesrvAddr + "  instanceName : " + instanceName);
        } catch (MQClientException e) {
            log.error("Producer start failed", e.getErrorMessage(), e);
            throw new RocketMQException(e);
        }

        return producer;

    }

}
