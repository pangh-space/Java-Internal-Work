package com.jiw.dudu.listener;

import com.jiw.dudu.event.UserRegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Description 用户注册成功，发送邮件
 * @Author pangh
 * @Date 2022年11月29日
 * @Version v1.0.0
 */
@Slf4j
public class SendEmailListener implements ApplicationListener<UserRegisterEvent> {
    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(UserRegisterEvent event) {
        log.info(String.format("给用户【%s】发送注册成功邮件!", event.getUserName()));
    }
}