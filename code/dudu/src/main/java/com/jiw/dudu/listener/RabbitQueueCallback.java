package com.jiw.dudu.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @Description
 * 1. RabbitMQ 交换机发送消息到队列的confirm机制确认回调
 * 2. 该接口只会接收到路由不到队列的消息
 * @Author pangh
 * @Date 2022年09月27日
 * @Version v1.0.0
 */
@Component
@Slf4j
public class RabbitQueueCallback implements RabbitTemplate.ReturnsCallback {
    /**
     * Returned message callback.
     *
     * @param returned the returned message and metadata.
     */
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.info("投递队列错误，消息 {} 经交换机 {} 通过routingKey={} 路由到队列失败，失败code为：{}， 失败原因为：{}",
                new String(returned.getMessage().getBody()),
                returned.getExchange(),
                returned.getRoutingKey(),
                returned.getReplyCode(),
                returned.getReplyText());
    }
}