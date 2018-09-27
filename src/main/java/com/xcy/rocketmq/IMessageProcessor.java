package com.xcy.rocketmq;

import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * Created by xuchunyang on 2018/9/6 10点34分
 */
public interface IMessageProcessor {

    public boolean handleMessage(MessageExt messageExt);

}
