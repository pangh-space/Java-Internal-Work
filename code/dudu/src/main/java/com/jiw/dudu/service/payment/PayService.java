package com.jiw.dudu.service.payment;

import com.jiw.dudu.enums.PaymentEnum;
import com.jiw.dudu.service.payment.impl.AliPayServiceImpl;
import com.jiw.dudu.service.payment.impl.JdPayServiceImpl;
import com.jiw.dudu.service.payment.impl.WeixinPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description PayService
 * @Author pangh
 * @Date 2022年08月09日
 * @Version v1.0.0
 */
@Service
@Slf4j
public class PayService {

    @Autowired
    private AliPayServiceImpl aliPayService;

    @Autowired
    private JdPayServiceImpl jdPayService;

    @Autowired
    private WeixinPayServiceImpl weixinPayService;


    /**
     * if..else判断,每增加一个支付方式就要增加一个判断，代码膨胀不好。
     *
     * @param code
     */
    public void doPaymentV1(String code) {
        if ("alibaba".equals(code)) {
            aliPayService.pay();
        } else if ("weixin".equals(code)) {
            weixinPayService.pay();
        } else if ("jd".equals(code)) {
            jdPayService.pay();
        } else {
            System.out.println("找不到支付方式: " + code + "\t该支付方式还没纳入支付渠道");
        }
    }

    /**
     * 枚举版
     *
     * @param code
     */
    public void doPaymentV2(String code) {
        PaymentEnum paymentEnum = PaymentEnum.getPaymentEnum(code);

        System.out.println(paymentEnum != null ? "枚举优化: " + paymentEnum.getCode() + "\t" + paymentEnum.getMessage()
                : "枚举优化找不到支付方式: " + code + "\t该支付方式还没纳入支付渠道");
    }

}