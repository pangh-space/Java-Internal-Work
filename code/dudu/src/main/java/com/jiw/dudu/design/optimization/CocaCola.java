package com.jiw.dudu.design.optimization;

import org.springframework.stereotype.Component;

/**
 * @Description CocaCola
 * @Author pangh
 * @Date 2023年07月12日
 * @Version v1.0.0
 */
@Component
public class CocaCola extends AbstractColaHandler{

    @Override
    public String cocaMethod(String name) {
        return "-----CocaCola类型，name :" + name;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ShareColaFactory.register(ColaTypeEnum.COCA_COLA,this);
    }
}