package com.jiw.dudu.factory;

import com.jiw.dudu.design.optimization.AbstractColaHandler;
import com.jiw.dudu.design.optimization.ColaTypeEnum;
import com.jiw.dudu.enums.FirmEnum;
import com.jiw.dudu.service.FirmService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description ShareFirmFactory
 * @Author pangh
 * @Date 2023年07月13日
 * @Version v1.0.0
 */
@Component
public class ShareFirmFactory {

    // 定义缓存策略
    private static final Map<FirmEnum, FirmService> firmStrategyMap = new HashMap<>(FirmEnum.values().length);

    // 注册策略模式
    public static void register(FirmEnum firmEnum,FirmService firmService){
        firmStrategyMap.put(firmEnum,firmService);
    }

    /**
     * 获取对应的策略
     *
     * @param firmEnum
     * @return
     */
    public static FirmService getColaStrategy(FirmEnum firmEnum){
        if(firmEnum == null){
            throw new IllegalArgumentException("type should not be null or empty.");
        }
        return firmStrategyMap.get(firmEnum);
    }
}