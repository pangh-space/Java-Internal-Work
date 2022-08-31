package com.jiw.dudu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Description DuduApplication
 * @Author pangh
 * @Date 2022年08月08日
 * @Version v1.0.0
 */
@SpringBootApplication
@MapperScan("com.jiw.dudu.mapper")
public class DuduApplication {

    public static void main(String[] args) {

        SpringApplication.run(DuduApplication.class,args);

    }

}