package com.jiw.dudu.design.optimization;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @Description AbstractColaHandler
 * @Author pangh
 * @Date 2023年07月12日
 * @Version v1.0.0
 */
@Component
public abstract class AbstractColaHandler implements InitializingBean {

    public String cocaMethod(String name){
        throw new UnsupportedOperationException();
    }

    public String pepsiMethod(String name){
        throw new UnsupportedOperationException();
    }

}