package com.jiw.dudu.controller;

import com.jiw.dudu.constants.RabbitMQ;
import com.jiw.dudu.listener.RabbitExchangeCallback;
import com.jiw.dudu.listener.RabbitQueueCallback;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @Description RabbitMQController
 * @Author pangh
 * @Date 2022年09月27日
 * @Version v1.0.0
 *
 * RabbitMQ消息队列参考地址：
 * 1. MQ交换机备份：https://blog.csdn.net/m0_62436868/article/details/126119662
 * 2. 消息发布确认机制：https://blog.csdn.net/u010502101/article/details/125986244
 * 3. 死信队列：https://blog.csdn.net/u010502101/article/details/125454485
 */
@Api(tags = "消息队列接口")
@RestController
@Slf4j
@RequestMapping(value = "mq")
public class MqController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitExchangeCallback rabbitExchangeCallback;

    @Autowired
    private RabbitQueueCallback rabbitQueueCallback;

    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(rabbitExchangeCallback);
        rabbitTemplate.setReturnsCallback(rabbitQueueCallback);
    }

    @ApiOperation("RabbitMQ写入消息")
    @RequestMapping(value = "fireMessageRabbitMQ",method = RequestMethod.GET)
    public String fireMessageRabbitMQ(){
        // 配置全部成功，消息发送成功
        String message1 = "组织架构变动1";
        CorrelationData correlationData = new CorrelationData();
        ReturnedMessage returnedMessage = new ReturnedMessage(new Message(message1.getBytes()),0,null,null,null);
        correlationData.setReturned(returnedMessage);
        rabbitTemplate.convertAndSend(RabbitMQ.ORG_USER_EXCHANGE, RabbitMQ.ORG_ROUTING_KEY, message1,correlationData);
        // 将routingKey配置错误，验证消息写入队列失败。验证消息投递错误，进入到备份队列
        String message2 = "组织架构变动2";
        CorrelationData correlationData1 = new CorrelationData();
        ReturnedMessage returnedMessage1 = new ReturnedMessage(new Message(message2.getBytes()),0,null,null,null);
        correlationData1.setReturned(returnedMessage1);
        rabbitTemplate.convertAndSend(RabbitMQ.ORG_USER_EXCHANGE, "userRouting_2", message2,correlationData1);
        // 将exchange配置错误，生产者发布消息到交换机失败
//        String message3 = "组织架构变动3";
//        CorrelationData correlationData3 = new CorrelationData();
//        ReturnedMessage returnedMessage3 = new ReturnedMessage(new Message(message3.getBytes()),0,"交换机填写错误",null,RabbitMQ.ORG_ROUTING_KEY);
//        correlationData1.setReturned(returnedMessage1);
//        rabbitTemplate.convertAndSend("dudu.direct.org.user.error", RabbitMQ.ORG_ROUTING_KEY, message3,correlationData3);

        return "ok";
    }

}