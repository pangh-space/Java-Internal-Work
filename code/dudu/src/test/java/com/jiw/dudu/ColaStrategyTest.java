package com.jiw.dudu;

import com.jiw.dudu.design.optimization.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description ColaStrategyTest
 * @Author pangh
 * @Date 2023年07月13日
 * @Version v1.0.0
 */
@SpringBootTest(classes = {DuduApplication.class})
@RunWith(SpringRunner.class)
public class ColaStrategyTest {

    @Test
    public void v3(){
        PepsiCola colaHandler = (PepsiCola) ShareColaFactory.getColaStrategy(ColaTypeEnum.PEPSI_COLA);
        System.out.println(colaHandler.pepsiMethod(ColaTypeEnum.PEPSI_COLA.getDesc()));
    }

}