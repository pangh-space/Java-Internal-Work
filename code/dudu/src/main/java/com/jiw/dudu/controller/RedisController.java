package com.jiw.dudu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description RedisController
 * @Author pangh
 * @Date 2022年09月16日
 * @Version v1.0.0
 */
@Api(tags = "缓存接口")
@RestController
@Slf4j
@RequestMapping(value = "redis")
public class RedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation("集群设置缓存数据")
    @RequestMapping(value = "addRedisKey/{redisKey}",method = RequestMethod.GET)
    public String addRedisKey(@PathVariable("redisKey") String redisKey){
        stringRedisTemplate.opsForValue().set(redisKey,"验证Redis集群功能");
        return "ok";
    }

}