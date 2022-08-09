package com.jiw.dudu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Description DuduApplication
 * @Author pangh
 * @Date 2022年08月08日
 * @Version v1.0.0
 */
@SpringBootApplication
public class DuduApplication {

    public static void main(String[] args) {

        SpringApplication.run(DuduApplication.class,args);

    }

}