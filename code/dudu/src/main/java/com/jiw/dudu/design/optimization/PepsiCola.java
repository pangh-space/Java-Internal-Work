package com.jiw.dudu.design.optimization;

import org.springframework.stereotype.Component;

/**
 * @Description PepsiCola
 * @Author pangh
 * @Date 2023年07月12日
 * @Version v1.0.0
 */
@Component
public class PepsiCola extends AbstractColaHandler{

    @Override
    public String pepsiMethod(String name) {
        return "-----PepsiCola类型，name :" + name;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ShareColaFactory.register(ColaTypeEnum.PEPSI_COLA,this);
    }
}