package com.jiw.dudu.config;

import com.jiw.dudu.constants.RabbitMQ;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description RabbitMQ 相关队列及交换机配置
 * @Author pangh
 * @Date 2022年09月27日
 * @Version v1.0.0
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 组织架构普通队列
     *
     * @return
     */
    @Bean
    public Queue OrgQueue() {
        // 默认是可以持久化的队列
        return QueueBuilder.durable(RabbitMQ.ORG_QUEUE)
                //声明该队列的死信消息发送到的 交换机 （队列添加了这个参数之后会自动与该交换机绑定，并设置路由键，不需要开发者手动设置)
                .withArgument("x-dead-letter-exchange", RabbitMQ.DLX_ORG_USER_EXCHANGE)
                //声明该队列死信消息在交换机的 路由键
                .withArgument("x-dead-letter-routing-key", RabbitMQ.DLX_ORG_ROUTING_KEY)
                // 设置队列数据过期时间，消息到期没有被消费，则进入死信队列
                .withArgument("x-message-ttl",10000)
                .build();
    }

    /**
     * 组织架构死信队列
     * 说明，进入死信队列的几个条件：
     * 1. 消息TTL过期，TTL指Time To Live，指消息在队列中存活的时间；
     * 2. 队列达到最大长度，队列已经满，无法再继续添加消息；
     * 3. 消息被拒接，消息被消费者消费后，消费者发送了basic.reject拒绝指令或者basic.nack否定指令。
     *
     * @return
     */
    @Bean
    public Queue DlxOrgQueue() {
        return new Queue(RabbitMQ.DLX_ORG_QUEUE, true);
    }

    /**
     * 组织架构普通备份队列，用于实现投递错误的消息进入该队列
     *
     * @return
     */
    @Bean
    public Queue BakOrgQueue() {
        // 默认是可以持久化的队列
        return new Queue(RabbitMQ.BAK_ORG_QUEUE, true);
    }

    /**
     * 员工普通队列
     *
     * @return
     */
    @Bean
    public Queue UserQueue() {
        return QueueBuilder.durable(RabbitMQ.USER_QUEUE)
                //声明该队列的死信消息发送到的 交换机 （队列添加了这个参数之后会自动与该交换机绑定，并设置路由键，不需要开发者手动设置)
                .withArgument("x-dead-letter-exchange", RabbitMQ.DLX_ORG_USER_EXCHANGE)
                //声明该队列死信消息在交换机的 路由键
                .withArgument("x-dead-letter-routing-key", RabbitMQ.DLX_USER_ROUTING_KEY)
                .build();
    }

    /**
     * 员工死信队列
     *
     * @return
     */
    @Bean
    public Queue DlxUserQueue() {
        // 默认是可以持久化的队列
        return new Queue(RabbitMQ.DLX_USER_QUEUE, true);
    }

    /**
     * 定义备份交换机，备份交换机为fanout 类型
     *
     * @return
     */
    @Bean
    FanoutExchange BakOrgUserExchange(){
        return new FanoutExchange(RabbitMQ.BAK_ORG_USER_EXCHANGE);
    }

    /**
     * 定义交换机
     *
     * @return
     */
    @Bean
    DirectExchange OrgUserExchange() {
        DirectExchange directExchange = new DirectExchange(RabbitMQ.ORG_USER_EXCHANGE, true, false);
        // 该交换机的备份交换机
        directExchange.addArgument("alternate-exchange",RabbitMQ.BAK_ORG_USER_EXCHANGE);
        return directExchange;
    }

    /**
     * 定义死信交换机
     *
     * @return
     */
    @Bean
    DirectExchange DlxOrgUserExchange() {
        return new DirectExchange(RabbitMQ.DLX_ORG_USER_EXCHANGE, true, false);
    }


    /**
     * 将队列与路由绑定-通过OrgRouting  匹配
     *
     * @return
     */
    @Bean
    Binding bindingOrgDirect() {
        return BindingBuilder.bind(OrgQueue()).to(OrgUserExchange()).with(RabbitMQ.ORG_ROUTING_KEY);
    }

    @Bean
    Binding bindingDlxOrgDirect() {
        return BindingBuilder.bind(DlxOrgQueue()).to(DlxOrgUserExchange()).with(RabbitMQ.DLX_ORG_ROUTING_KEY);
    }

    /**
     * 绑定备份队列与备份交换机
     *
     * @return
     */
    @Bean
    Binding bindingBakOrgDirect() {
        return BindingBuilder.bind(BakOrgQueue()).to(BakOrgUserExchange());
    }

    @Bean
    Binding bindingUserDirect() {
        return BindingBuilder.bind(UserQueue()).to(OrgUserExchange()).with(RabbitMQ.USER_ROUTING_KEY);
    }

    @Bean
    Binding bindingDlxUserDirect() {
        return BindingBuilder.bind(DlxUserQueue()).to(DlxOrgUserExchange()).with(RabbitMQ.DLX_USER_ROUTING_KEY);
    }

}