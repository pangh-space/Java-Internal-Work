package com.jiw.dudu.aop;

import cn.hutool.json.JSONUtil;
import com.jiw.dudu.annotations.LogRecordAnnotation;
import com.jiw.dudu.entities.logs.Convert;
import com.jiw.dudu.entities.logs.LogRecord;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description LogRecordAspect
 * @Author pangh
 * @Date 2022年11月23日
 * @Version v1.0.0
 */
@Aspect
@Component
public class LogRecordAspect {

    /**
     * 切面类+连接点joinpoint+切入点pointCut
     */
    @Pointcut("@annotation(com.jiw.dudu.annotations.LogRecordAnnotation)")
    public void pointCut(){}

    //设计时，一定要注意异步，横切性关注点，另外用线程处理，和主线程解耦，不打扰
    ExecutorService threadPool = new ThreadPoolExecutor(
            1,
            1,
            1L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(200));

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        Object proceed = joinPoint.proceed();

        Object finalProceed = proceed;

        // 异步执行机制记录逻辑
        threadPool.execute(()->{
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            LogRecordAnnotation logRecordAnnotation = methodSignature.getMethod().getDeclaredAnnotation(LogRecordAnnotation.class);


            Class<? extends Convert> convert = logRecordAnnotation.convert();
            try {
                Convert logConvert = convert.newInstance();
                System.out.println("logConvert----"+logConvert.toString());
                System.out.println("----参数： "+ joinPoint.getArgs()[0]);

                LogRecord logRecord = logConvert.covert(joinPoint.getArgs()[0]);

                logRecord.setDesc(logRecordAnnotation.desc());
                logRecord.setResult(finalProceed.toString());
                System.out.println("===记录流水： "+ JSONUtil.toJsonStr(logRecord));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        });

        return proceed;
    }

}