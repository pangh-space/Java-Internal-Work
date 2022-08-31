package com.jiw.dudu.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 *
 */
public enum PaymentEnum
{
    ALIBABA("alibaba", "支付宝付款"),
    WEINXIN("weixin", "微信付款"),
    TAOBAO("taobao", "淘宝付款"),
    JD("jd", "京东付款");


    @Getter
    private String code;
    @Getter
    private String message;

    PaymentEnum(String code, String message)
    {
        this.code = code;
        this.message = message;
    }

    //枚举的遍历和查找
    public static PaymentEnum getPaymentEnum(String code){
        return Arrays.stream(PaymentEnum.values()).filter(x -> x.code.equalsIgnoreCase(code)).findFirst().orElse(null);
    }


}
