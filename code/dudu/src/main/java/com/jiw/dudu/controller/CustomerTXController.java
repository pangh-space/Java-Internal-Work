package com.jiw.dudu.controller;

import com.jiw.dudu.entities.Customer;
import com.jiw.dudu.service.CustomerTXService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

/**
 * @Description 对于@Transactional 事务各种情况的测试案例
 * @Author pangh
 * @Date 2022年08月31日
 * @Version v1.0.0
 */
@Api(tags = "客户Customer接口")
//@Api(tags = "客户Customer对于@Transactional事务测试案例")
@RestController
@Slf4j
public class CustomerTXController {

    @Autowired
    private CustomerTXService customerTXService;

    @ApiOperation("新增1条数据")
    @RequestMapping(value = "/customertx/add", method = RequestMethod.POST)
    public void addCustomer() throws Exception {

        Customer customer = new Customer();

        customer.setCname("customerTX测试");
        customer.setAge(new Random().nextInt(20) + 1);
        customer.setPhone(getTel());
        customer.setSex((byte) new Random().nextInt(2));
        customer.setBirth(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

        log.info("-----hahhahha，笑O(∩_∩)O哈哈~");
//        customerTXService.addCustomer(customer);
        customerTXService.addCustomer1(customer);
//        customerTXService.addCustomer41(customer);
    }


    // 以下为临时生成手机号方法
    private static String[] telFirst = ("134,135,136,137,138,139,150,151,152," +
            "157,158,159,130,131,132,155,156,133,153").split(",");

    private static String getTel() {
        int index = getNum(0, telFirst.length - 1);
        String first = telFirst[index];
        String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
        String third = String.valueOf(getNum(1, 9100) + 10000).substring(1);
        return first + second + third;
    }

    public static int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

}