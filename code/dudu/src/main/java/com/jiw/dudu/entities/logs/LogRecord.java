package com.jiw.dudu.entities.logs;

import lombok.Data;

/**
 * @Description LogRecord
 * @Author pangh
 * @Date 2022年11月23日
 * @Version v1.0.0
 */
@Data
public class LogRecord {

    // 业务ID
    private String id;
    // 名称
    private String name;
    // 描述
    private String desc;
    // 结果
    private String result;

}