package com.jiw.dudu.entities.logs;

/**
 * @Description Convert
 * @Author pangh
 * @Date 2022年11月23日
 * @Version v1.0.0
 */
public interface Convert<PARAM> {

    LogRecord covert(PARAM param);

}