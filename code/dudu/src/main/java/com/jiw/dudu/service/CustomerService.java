package com.jiw.dudu.service;

import cn.hutool.core.date.StopWatch;
import com.jiw.dudu.entities.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * @Description CustomerService
 * @Author pangh
 * @Date 2022年08月10日
 * @Version v1.0.0
 */
@Service
@Slf4j
public class CustomerService {

    public Customer findCustomerByCompletableFuture(String typeId) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 15, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(30));
        StopWatch stopWatch = new StopWatch("CompletableFeture并行任务");
        if (typeId.equalsIgnoreCase("0")) {
            stopWatch.start("同步调用");
            this.getCustomerInfo();
            this.getScore();
            this.getOrderInfo();
            stopWatch.stop();
            log.info("没用CompletableFuture时间成本:{}", stopWatch.prettyPrint(TimeUnit.MILLISECONDS) + " 毫秒");
            return new Customer(0, "没用CompletableFuture时间成本案例:" + stopWatch.prettyPrint(TimeUnit.MILLISECONDS) + " 毫秒");
        } else {
            stopWatch.start("异步调用");

            CompletableFuture<Boolean> customerInfoCompletable = CompletableFuture.supplyAsync(() -> {
                System.out.println(Thread.currentThread().getName() + "\t" + "1");
                this.getCustomerInfo();
                return Boolean.TRUE;
            }, threadPoolExecutor);
            CompletableFuture<Boolean> scoreCompletable = CompletableFuture.supplyAsync(() -> {
                System.out.println(Thread.currentThread().getName() + "\t" + "2");
                this.getScore();
                return Boolean.TRUE;
            }, threadPoolExecutor);
            CompletableFuture<Boolean> orderInfoCompletable = CompletableFuture.supplyAsync(() -> {
                System.out.println(Thread.currentThread().getName() + "\t" + "3");
                this.getOrderInfo();
                return Boolean.TRUE;
            }, threadPoolExecutor);

            CompletableFuture.allOf(customerInfoCompletable, scoreCompletable, orderInfoCompletable).join();
            long costTimeResult = Long.parseLong(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
            log.info("使用CompletableFuture时间成本:{}", costTimeResult + " 毫秒");
            return new Customer(1, "使用CompletableFuture时间成本案例:" + costTimeResult + " 毫秒");
        }
    }

    protected void getCustomerInfo() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void getScore() {
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void getOrderInfo() {
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Async(value = "threadPoolExecutorTask")
    public Future<String> getCustomerInfoAsync() {
        log.info("获取客户信息......开始");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("获取客户信息......结束");

        return new AsyncResult<>("获取客户信息 完成");
    }

    @Async(value = "threadPoolExecutorTask")
    public Future<String> getScoreAsync() {
        log.info("获取评分信息......开始");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("获取评分信息......结束");

        return new AsyncResult<>("获取评分信息 完成");
    }

    @Async(value = "threadPoolExecutorTask")
    public Future<String> getOrderInfoAsync() {
        log.info("获取订单信息......开始");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("获取订单信息......结束");

        return new AsyncResult<>("获取订单信息 完成");
    }

}