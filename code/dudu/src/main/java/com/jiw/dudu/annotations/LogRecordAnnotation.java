package com.jiw.dudu.annotations;

import com.jiw.dudu.entities.logs.Convert;

import java.lang.annotation.*;

/**
 * @Description 日志记录注解
 * @Author pangh
 * @Date 2022年11月23日
 * @Version v1.0.0
 *
 * 每个方法加入LogRecordAnnotation，就可以把操作流水记录下来
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogRecordAnnotation {

    String desc() default "";

    Class<? extends Convert> convert();

}
