package com.dudu.service;

import com.spring.BeanNameAware;
import com.spring.BeanPostProcessor;
import com.spring.Component;

/**
 * @Description UserService
 * @Author pangh
 * @Date 2022年11月21日
 * @Version v1.0.0
 */
@Component("userService")
public class UserService implements BeanNameAware, BeanPostProcessor {

    private String beanName;

    @Override
    public void setBeanName(String beanName) {
        System.out.println("调用BeanNameAware方法...");
        this.beanName = beanName;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("通过BeanPostProcessor获取到的Bean实例：" + bean);
        return bean;
    }

    public void printBeanName(){
        System.out.println(beanName);
    }
}