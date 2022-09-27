package com.jiw.dudu.consumer;

import com.jiw.dudu.constants.RabbitMQ;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description RabbitMsgConsumer
 * @Author pangh
 * @Date 2022年09月27日
 * @Version v1.0.0
 */
@Component
@Slf4j
public class RabbitMsgConsumer {

    /**
     * 监听组织机构队列
     * @RabbitHandler（注解标注的方法要增加 channel(信道)、message 两个参数）
     * @RabbitListener 监听队列
     * @param message
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitMQ.ORG_QUEUE)
    @SneakyThrows
    public void onMessage(String hello, Channel channel, Message message) {
        log.info("正常消费组织架构消息，时间：" + System.currentTimeMillis() + ",消息内容：" + hello);


        // 自动ACK，模拟发生异常，服务端配置重试3次后，放入死信队列
//        throw new RuntimeException("模拟发生异常");

        // 模拟手动ACK，出现异常后拒收消息。服务端放入死信队列
        if (hello != null) {
            // 第三个参数：false ，消费失败-直接绝收，服务端放入死信队列
            // 第三个参数：true ， 消费失败，放入队列，等待再次消费
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        } else {
            // 消费成功，给服务端ACK确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }

    }

    @RabbitHandler
    @RabbitListener(queues = RabbitMQ.BAK_ORG_QUEUE)
    @SneakyThrows
    public void onExceptionMessage(String msg,Channel channel,Message message){
        log.info("异常消费消息，消息内容：{}",msg);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        // 确认消费成功消息，确认成功以后，消息队列会删除响应的消息
//        channel.basicAck(deliveryTag,false);

        /**
         * 表示失败确认，一般在消费消息业务异常时用到此方法，可以将消息重新投递入队列。
         * deliveryTag：表示消息投递序号;multiple：是否批量确认;requeue：值为 true 消息将重新入队列。
         *
         * 注意：此处有坑，消息被再次放入队列都，客户端会再次消费，如此往复会出现死循环
         */
//        channel.basicNack(deliveryTag,false,true);

    }

}