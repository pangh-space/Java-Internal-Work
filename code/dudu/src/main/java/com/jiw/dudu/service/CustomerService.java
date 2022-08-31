package com.jiw.dudu.service;

import com.jiw.dudu.entities.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description CustomerService
 * @Author pangh
 * @Date 2022年08月10日
 * @Version v1.0.0
 */
@Service
@Slf4j
public class CustomerService {

    public Customer findCustomerByCompletableFuture(String typeId){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 15, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(30));
        if(typeId.equalsIgnoreCase("0")){
            long startTime = System.currentTimeMillis();
            this.getCustomerInfo();
            this.getScore();
            this.getOrderInfo();
            long endTime = System.currentTimeMillis();
            log.info("没用CompletableFuture时间成本:{}",(endTime - startTime) +" 毫秒");
            return new Customer(0,"没用CompletableFuture时间成本案例:"+(endTime - startTime) +" 毫秒");
        }else{
            long startTime = System.currentTimeMillis();

            CompletableFuture<Boolean> customerInfoCompletable = CompletableFuture.supplyAsync(() -> {
                System.out.println(Thread.currentThread().getName() + "\t" + "1");
                this.getCustomerInfo();
                return Boolean.TRUE;
            }, threadPoolExecutor);
            CompletableFuture<Boolean> scoreCompletable = CompletableFuture.supplyAsync(()->{
                System.out.println(Thread.currentThread().getName() + "\t" + "2");
                this.getScore();
                return Boolean.TRUE;
            },threadPoolExecutor);
            CompletableFuture<Boolean> orderInfoCompletable = CompletableFuture.supplyAsync(()->{
                System.out.println(Thread.currentThread().getName() + "\t" + "3");
                this.getOrderInfo();
                return Boolean.TRUE;
            },threadPoolExecutor);

            CompletableFuture.allOf(customerInfoCompletable,scoreCompletable,orderInfoCompletable).join();
            long endTime = System.currentTimeMillis();
            long costTimeResult = endTime - startTime;
            log.info("使用CompletableFuture时间成本:{}",costTimeResult +" 毫秒");
            return new Customer(1,"使用CompletableFuture时间成本案例:"+costTimeResult +" 毫秒");
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

}