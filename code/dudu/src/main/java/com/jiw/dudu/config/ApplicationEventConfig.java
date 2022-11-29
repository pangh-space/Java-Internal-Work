package com.jiw.dudu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @Description 为用户注册异步监听器异步执行提供支持
 * @Author pangh
 * @Date 2022年11月29日
 * @Version v1.0.0
 */
@Configuration
public class ApplicationEventConfig {

    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster(@Qualifier("threadPoolExecutorTask") ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        //创建一个事件广播器
        SimpleApplicationEventMulticaster result = new SimpleApplicationEventMulticaster();
        //设置异步执行器
        result.setTaskExecutor(threadPoolTaskExecutor);
        return result;
    }

}