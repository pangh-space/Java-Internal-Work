package com.dudu;

import com.dudu.service.UserService;
import com.spring.DuduApplicationContext;

/**
 * @Description Test
 * @Author pangh
 * @Date 2022年11月20日
 * @Version v1.0.0
 */
public class Test {

    public static void main(String[] args) {
        DuduApplicationContext duduApplicationContext = new DuduApplicationContext(AppConfig.class);

        UserService userService = (UserService)duduApplicationContext.getBean("userService");
        System.out.println(userService);
        userService.printBeanName();
    }



}