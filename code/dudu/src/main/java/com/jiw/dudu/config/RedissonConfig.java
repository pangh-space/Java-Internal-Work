package com.jiw.dudu.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * @Description RedissonConfig
 * @Author pangh
 * @Date 2022年09月01日
 * @Version v1.0.0
 */
@Configuration
public class RedissonConfig {

//    @Value("${spring.redis.host}")
//    private String host;
//
//    @Value("${spring.redis.port}")
//    private String port;
//
//    @Value("${spring.redis.password}")
//    private String password;
//
//    @Value("${spring.redis.database}")
//    private int database;
//
//    @Bean
//    public Redisson redisson() {
//        // 此为单机模式
//        Config config = new Config();
//        config.useSingleServer()
//                .setAddress("redis://"+host+":"+port)
//                .setPassword(password)
//                .setDatabase(database);
//        return (Redisson) Redisson.create(config);
//    }


}