package com.jiw.dudu.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description ProductController
 * @Author pangh
 * @Date 2022年09月01日
 * @Version v1.0.0
 */
@Api(tags = "商品相关")
@RestController
@Slf4j
@RequestMapping(value = "product")
public class ProductController {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * Redisson 做分布式锁，防止商品超卖
     *
     * @return
     */
    @RequestMapping("/deductStock")
    public String deductStock() {
        // 相应的商品唯一Key
        String lockKey = "lock:product_101";
        //获取锁对象
        RLock redissonLock = redissonClient.getLock(lockKey);
        //加分布式锁
        redissonLock.lock();
        try {
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                log.info("扣减成功，剩余库存:" + realStock);
            } else {
                log.info("扣减失败，库存不足");
            }
        } finally {
            //解锁
            redissonLock.unlock();
        }
        return "end";
    }

}