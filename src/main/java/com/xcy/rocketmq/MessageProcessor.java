package com.xcy.rocketmq;

import com.alibaba.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by xuchunyang on 2018/9/6 10点35分
 */
@Component
public class MessageProcessor implements IMessageProcessor {


    private static final Logger log = LoggerFactory.getLogger(MessageProcessor.class);

    @Override
    public boolean handleMessage(MessageExt messageExt) {

        System.out.println("Consume Message [ topic : "+ messageExt.getTopic() +
                        " key :" + messageExt.getKeys() +
                        " tag: " + messageExt.getTags() +
                        " body: " + new String(messageExt.getBody())+" ] ");
//        log.info("Consume Message [ topic : "+ messageExt.getTopic() +
//                        " key :" + messageExt.getKeys() +
//                        " tag: " + messageExt.getTags() +
//                        " body: " + new String(messageExt.getBody())+" ] ");


        return true;
    }

}
