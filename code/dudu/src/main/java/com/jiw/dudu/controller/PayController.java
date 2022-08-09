package com.jiw.dudu.controller;

import com.jiw.dudu.service.payment.PayService;
import com.jiw.dudu.service.payment.PayServiceV3;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 多种支付方式调用
 * @Author pangh
 * @Date 2022年08月08日
 * @Version v1.0.0
 */

@Api(description = "客户Pay接口")
@RestController
@Slf4j
public class PayController {

    @Autowired
    private PayService payService;

    @Autowired
    private PayServiceV3 payServiceV3;

    @ApiOperation("支付设计模式调用案例V1:if-else")
    @RequestMapping(value = "/pay/{code}", method = RequestMethod.GET)
    public void payV1(@PathVariable("code") String code) {
        log.info("前台选择付渠道V1:{}", code);
        payService.doPaymentV1(code);
    }

    @ApiOperation("支付设计模式调用案例V2:枚举")
    @RequestMapping(value = "/payv2/{code}", method = RequestMethod.GET)
    public void payV2(@PathVariable("code") String code) {
        log.info("前台选择付渠道V2:{}", code);
        payService.doPaymentV2(code);
    }

    @ApiOperation("支付设计模式调用案例V3:spring自动加载")
    @RequestMapping(value = "/payv3/{code}", method = RequestMethod.GET)
    public void payV3(@PathVariable("code") String code) {
        log.info("前台选择付渠道V3:{}",code);
        payServiceV3.doPaymentV3(code);
    }

}