package com.jiw.dudu.controller;

import cn.hutool.core.date.StopWatch;
import com.jiw.dudu.entities.Customer;
import com.jiw.dudu.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Description 异步并行调用实现方式
 * 1. 使用CompletableFeture实现并行调用
 * 2. 使用@Async 实现异步并行调用
 * @Author pangh
 * @Date 2022年08月10日
 * @Version v1.0.0
 */
@Api(tags = "客户Customer接口，实现并行调用")
@RestController
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 使用CompletableFeture实现异步调用
     *
     * @param typeId
     * @return
     */
    @ApiOperation("CompletableFuture多接口并行调用案例")
    @RequestMapping(value = "/customercf/{typeId}", method = RequestMethod.GET)
    public Customer findCustomerByCompletableFuture(@PathVariable("typeId") String typeId) {
        return customerService.findCustomerByCompletableFuture(typeId);
    }


    @ApiOperation("Spring@Async异步回调+自定义线程池实现多接口并行调用案例")
    @GetMapping("/findCustomerBySpringAsync")
    public String findCustomerBySpringAsync() {

        StopWatch stopWatch = new StopWatch("异步任务执行");
        stopWatch.start("Spring Async");
        // 用来判断三个任务全部执行完成
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        Future<String> future1 = customerService.getCustomerInfoAsync();
        Future<String> future2 = customerService.getScoreAsync();
        Future<String> future3 = customerService.getOrderInfoAsync();

        while (true) {
            if (future1.isDone() && future2.isDone() && future3.isDone()) {
                atomicBoolean.set(true);
                break;
            }
            //每间隔1秒询问一次是否干完
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (atomicBoolean.get()) {
            log.info("3个任务结合线程池都处理完成^_^");
        }
        stopWatch.stop();
        log.info("执行时长{}",stopWatch.prettyPrint(TimeUnit.SECONDS));
        return "findCustomerBySpringAsync is over" + "\t" + UUID.randomUUID().toString();
    }


}