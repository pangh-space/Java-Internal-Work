package com.jiw.dudu.event;

import org.springframework.context.ApplicationEvent;

/**
 * @Description 用户注册事件
 * @Author pangh
 * @Date 2022年11月29日
 * @Version v1.0.0
 */
public class UserRegisterEvent extends ApplicationEvent {

    /**
     * 用户名
     */
    private String userName;

    public UserRegisterEvent(Object source, String userName) {
        super(source);
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

}