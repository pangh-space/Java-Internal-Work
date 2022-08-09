package com.jiw.dudu.service.payment.impl;

import com.jiw.dudu.service.payment.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description 支付宝付款具体实现
 * @Author pangh
 * @Date 2022年08月09日
 * @Version v1.0.0
 */
@Service
@Slf4j
public class AliPayServiceImpl implements IPayService {
    /**
     * 统一支付接口
     */
    @Override
    public void pay() {
        log.info("支付宝付款...");
    }
}