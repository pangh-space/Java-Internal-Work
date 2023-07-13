package com.jiw.dudu.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @Description FirmService
 * @Author pangh
 * @Date 2023年07月13日
 * @Version v1.0.0
 */
public interface FirmService extends InitializingBean {

    String getKnowledge(String question);

}