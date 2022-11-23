package com.jiw.dudu.entities.logs;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description RoundTable
 * @Author pangh
 * @Date 2022年11月23日
 * @Version v1.0.0
 */
@Data
public class RoundTable implements Serializable,Convert<RoundTable> {

    /**
     * 圆桌ID
     */
    private String roundTableId;

    @Override
    public LogRecord covert(RoundTable roundTable) {
        LogRecord logRecord = new LogRecord();
        logRecord.setId(roundTable.getRoundTableId());
        return logRecord;
    }
}