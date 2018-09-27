package com.xcy.rocketmq;

/**
 * Created by xuchunyang on 2018/9/6 10点47分
 */
public class RocketMQException extends Exception {

    public RocketMQException() {
        super();
    }

    public RocketMQException(String message) {
        super(message);
    }

    public RocketMQException(String message, Throwable cause) {
        super(message, cause);
    }

    public RocketMQException(Throwable cause) {
        super(cause);
    }

    public RocketMQException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
