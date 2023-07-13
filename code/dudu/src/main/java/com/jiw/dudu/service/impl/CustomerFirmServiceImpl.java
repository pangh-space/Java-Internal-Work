package com.jiw.dudu.service.impl;

import com.jiw.dudu.enums.FirmEnum;
import com.jiw.dudu.factory.ShareFirmFactory;
import com.jiw.dudu.service.FirmService;
import org.springframework.stereotype.Service;

/**
 * @Description CustomerFirmServiceImpl
 * @Author pangh
 * @Date 2023年07月13日
 * @Version v1.0.0
 */
@Service
public class CustomerFirmServiceImpl implements FirmService {
    @Override
    public String getKnowledge(String question) {
        return String.format("综合客服提问内容：%s ，回答综合客服提问问题...",question);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ShareFirmFactory.register(FirmEnum.ZONGHE_KEFU,this);
    }
}