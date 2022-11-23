package com.jiw.dudu.service;

import com.jiw.dudu.annotations.LogRecordAnnotation;
import com.jiw.dudu.entities.logs.SpecialTopic;
import org.springframework.stereotype.Service;

/**
 * @Description SpecialTopicService
 * @Author pangh
 * @Date 2022年11月23日
 * @Version v1.0.0
 */
@Service
public class SpecialTopicService {

    @LogRecordAnnotation(desc = "专题保存",convert = SpecialTopic.class)
    public boolean saveSpecialTopic(SpecialTopic specialTopic){
        System.out.println("专题插入操作，专题ID" + specialTopic.getSpecialTopicId());
        return true;
    }

}