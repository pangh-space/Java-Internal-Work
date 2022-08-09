package com.jiw.dudu.service.payment;

import com.jiw.dudu.annotations.PayCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description PayServiceV3
 * @Author pangh
 * @Date 2022年08月09日
 * @Version v1.0.0
 */
@Service
@Slf4j
public class PayServiceV3 implements ApplicationListener<ContextRefreshedEvent> {

    private static Map<String, IPayService> payMap = null;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(PayCode.class);

        if (beansWithAnnotation != null) {
            payMap = new HashMap<>();
            beansWithAnnotation.forEach((key, value) -> {
                //JdPayService    com.zzyy.study.service.payment.JdPayService@oiuwo2039
                System.out.println("key:" + key + "\t" + "value" + value);

                String code = value.getClass().getAnnotation(PayCode.class).code();
                String message = value.getClass().getAnnotation(PayCode.class).message();
                //code ="jd" message="京东付款PayCode注解"
                System.out.println(code + "\t" + message);
                //第一种实现：code 就是注解code的配置值(code ="jd")，value就是接口实现类 JdPayService implements IPay
                payMap.put(code, (IPayService) value);
            });
        }
    }

    /**
     * 第三种支付方式
     *
     * @param code
     */
    public void doPaymentV3(String code){
        payMap.get(code).pay();
    }
}