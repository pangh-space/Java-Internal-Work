package com.jiw.dudu.entities.logs;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description SpecialTopic
 * @Author pangh
 * @Date 2022年11月23日
 * @Version v1.0.0
 */
@Data
public class SpecialTopic implements Serializable,Convert<SpecialTopic> {

    /**
     * 专题ID
     */
    private String specialTopicId;

    @Override
    public LogRecord covert(SpecialTopic specialTopic) {
        LogRecord logRecord = new LogRecord();
        logRecord.setId(specialTopic.getSpecialTopicId());
        return logRecord;
    }
}