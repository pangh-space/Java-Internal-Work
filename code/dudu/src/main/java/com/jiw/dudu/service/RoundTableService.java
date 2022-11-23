package com.jiw.dudu.service;

import com.jiw.dudu.annotations.LogRecordAnnotation;
import com.jiw.dudu.entities.logs.RoundTable;
import org.springframework.stereotype.Service;

/**
 * @Description RoundTableService
 * @Author pangh
 * @Date 2022年11月23日
 * @Version v1.0.0
 */
@Service
public class RoundTableService {

    @LogRecordAnnotation(desc = "圆桌保存",convert = RoundTable.class)
    public boolean saveRoundTable(RoundTable roundTable){
        System.out.println("圆桌插入操作，圆桌ID" + roundTable.getRoundTableId());
        return true;
    }

}