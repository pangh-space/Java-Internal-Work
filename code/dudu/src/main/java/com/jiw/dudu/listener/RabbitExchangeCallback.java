package com.jiw.dudu.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @Description RabbitMQ 生产者发送消息到交换机的confirm机制确认回调
 * @Author pangh
 * @Date 2022年09月27日
 * @Version v1.0.0
 */
@Component
@Slf4j
public class RabbitExchangeCallback implements RabbitTemplate.ConfirmCallback {

    /**
     * Confirmation callback.
     *
     * @param correlationData correlation data for the callback.
     * @param ack             true for ack, false for nack
     * @param cause           An optional cause, for nack, when available, otherwise null.
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//        log.info("RabbitMQ交换机接收消息确认回调...");
        // 消息ID
        String id = correlationData.getId();
        // 消息
        String message = new String(correlationData.getReturned().getMessage().getBody());
        if(ack){
//            log.info("交换机收到消息ID为{}，消息内容为{}",id,message);
        }else{
//            log.info("交换机未收到消息ID为{}，消息内容为{}，原因为{}",id,message,cause);
        }
    }
}