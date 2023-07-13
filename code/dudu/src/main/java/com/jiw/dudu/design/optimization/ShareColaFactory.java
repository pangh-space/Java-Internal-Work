package com.jiw.dudu.design.optimization;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description ShareColaFactory
 * @Author pangh
 * @Date 2023年07月12日
 * @Version v1.0.0
 */
@Component
public class ShareColaFactory {

    // 定义策略Map缓存
    private static final Map<ColaTypeEnum,AbstractColaHandler> colaStrategyMap = new HashMap<>(ColaTypeEnum.values().length);

    public static void register(ColaTypeEnum colaTypeEnum,AbstractColaHandler abstractColaHandler){
        colaStrategyMap.put(colaTypeEnum,abstractColaHandler);
    }

    public static AbstractColaHandler getColaStrategy(ColaTypeEnum typeEnum){
        if(typeEnum == null){
            throw new IllegalArgumentException("type should not be null or empty.");
        }
        return colaStrategyMap.get(typeEnum);
    }


}