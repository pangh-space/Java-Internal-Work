package com.jiw.dudu;

import com.jiw.dudu.entities.Customer;
import com.jiw.dudu.service.CustomerTXService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

/**
 * @Description CustomerTXControllerTest
 * @Author pangh
 * @Date 2022年08月31日
 * @Version v1.0.0
 */
@SpringBootTest(classes = {DuduApplication.class})
@RunWith(SpringRunner.class)
@Slf4j
public class CustomerTXControllerTest {

    @Resource
    private CustomerTXService customerTXService;

    @Test
    public void addCustomer() throws Exception{
        Customer customer = new Customer();
        customer.setCname("customerTX测试");
        customer.setAge(new Random().nextInt(20)+1);
        customer.setPhone(getTel());
        customer.setSex((byte) new Random().nextInt(2));
        customer.setBirth(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

        /*标准版：无事务等操作，直接插入*/
//        customerTXService.addCustomer(customer);

        /*标准版：RuntimeException异常后事务回滚，如果一场被吃掉，则事务事务不会回滚*/
//        customerTXService.addCustomer1(customer);

        /*应用在非public 方法上，不可用*/
//        customerTXService.addCustomer2(customer);

        /*异常不匹配，对检查性异常不敏感，rollbackFor必须要和抛出的异常类型匹配*/
//        customerTXService.addCustomer3(customer);

        /*多线程和事务回滚,父线程异常，子线程正常。事务不会回滚*/
//        customerTXService.addCustomer41(customer);

        /*多线程和事务回滚,父线程正常，子线程异常。事务不会回滚*/
//        customerTXService.addCustomer42(customer);

        /*异常被内部catch没有抛出，事务不回回滚，如果抛出则事务回滚*/
//        customerTXService.addCustomer5(customer);


    }

    @Test
    public void addCustomerAndOrder() throws Exception{
        Customer customer = new Customer();

        customer.setCname("addCustomerAndOrderTX测试");
        customer.setAge(new Random().nextInt(20)+1);
        customer.setPhone(getTel());
        customer.setSex((byte) new Random().nextInt(2));
        customer.setBirth(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

        /*事务嵌套：V6.1，主子方法都有事务注解，异常在子方法*/
//        customerTXService.addCustomerAndOrder61(customer);

        /*事务嵌套：V6.2，主子方法都有事务注解，异常在主方法*/
//        customerTXService.addCustomerAndOrder62(customer);

        /*事务嵌套：V6.3，只有主方法有事务注解，异常在子方法*/
//        customerTXService.addCustomerAndOrder63(customer);

        /*事务嵌套：V6.4，只有主方法有事务注解，异常在主方法*/
//        customerTXService.addCustomerAndOrder64(customer);

        /*事务嵌套：V6.5，只有子方法有事务注解，异常在子方法*/
        customerTXService.addCustomerAndOrder65(customer);
    }


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