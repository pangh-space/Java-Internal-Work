package com.jiw.dudu.listener;

import com.jiw.dudu.event.UserRegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Description 用户注册相关监听器
 * @Author pangh
 * @Date 2022年11月29日
 * @Version v1.0.0
 */
@Slf4j
@Component
public class UserRegisterListener {

    /**
     * 发送邮件，可以使用Or  der注解来指定排序
     *
     * @param event
     */
    @Order(1)
    @EventListener
    public void sendMail(UserRegisterEvent event) {
        log.info(String.format(String.format("【%s】，给用户【%s】发送注册成功邮件!", Thread.currentThread(), event.getUserName())));
    }

    /**
     * 发送优惠券
     *
     * @param event
     */
    @Order(2)
    @EventListener
    public void sendCompon(UserRegisterEvent event) {
        log.info(String.format("【%s】，给用户【%s】发送优惠券!", Thread.currentThread(), event.getUserName()));
    }

}