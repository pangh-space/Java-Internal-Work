package com.jiw.dudu;

import com.jiw.dudu.service.user.UserRegisterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Description LogRecordTest
 * @Author pangh
 * @Date 2022年11月23日
 * @Version v1.0.0
 */
@SpringBootTest(classes = {DuduApplication.class})
@RunWith(SpringRunner.class)
public class ApplicationListenerTest {

    @Resource
    private UserRegisterService userRegisterService;

    @Test
    public void testUserRegisterFireMsg(){
        userRegisterService.registerUser("嘟嘟");
    }

}