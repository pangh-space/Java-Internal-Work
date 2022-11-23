package com.spring;

/**
 * @Description BeanPostProcess
 * @Author pangh
 * @Date 2022年11月23日
 * @Version v1.0.0
 */
public interface BeanPostProcessor {

    default Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    default Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }

}