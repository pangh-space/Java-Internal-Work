package com.jiw.dudu.service.user;

import com.jiw.dudu.event.UserRegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @Description 用户注册服务
 * 1. 用于验证用户注册成功后，发送邮件给用户
 * @Author pangh
 * @Date 2022年11月29日
 * @Version v1.0.0
 */
@Slf4j
@Component
public class UserRegisterService implements ApplicationEventPublisherAware {

    /**
     * 注意：该业务类实现了ApplicationEventPublisherAware接口，
     * spring容器会通过setApplicationEventPublisher方法
     * 将ApplicationEventPublisher注入进来，然后我们就可以使用这个来发布事件了。
     */
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 负责用户注册及发布事件的功能
     *
     * @param userName 用户名
     */
    public void registerUser(String userName) {
        /**
         * 此处省略用户注册业务
         */
        log.info(String.format("【%s】，用户【%s】注册成功", Thread.currentThread(),userName));
        //发布注册成功事件
        this.applicationEventPublisher.publishEvent(new UserRegisterEvent(this, userName));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}