package com.jiw.dudu.service.payment.impl;

import com.jiw.dudu.annotations.PayCode;
import com.jiw.dudu.service.payment.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description 微信付款具体实现
 * @Author pangh
 * @Date 2022年08月09日
 * @Version v1.0.0
 */
@PayCode(code ="alibaba",message="微信付款PayCode注解")
@Service
@Slf4j
public class WeixinPayServiceImpl implements IPayService {
    /**
     * 统一支付接口
     */
    @Override
    public void pay() {
        log.info("微信付款...");
    }
}