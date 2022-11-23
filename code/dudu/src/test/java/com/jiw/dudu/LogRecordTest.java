package com.jiw.dudu;

import com.jiw.dudu.entities.logs.RoundTable;
import com.jiw.dudu.entities.logs.SpecialTopic;
import com.jiw.dudu.service.RoundTableService;
import com.jiw.dudu.service.SpecialTopicService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description LogRecordTest
 * @Author pangh
 * @Date 2022年11月23日
 * @Version v1.0.0
 */
@SpringBootTest(classes = {DuduApplication.class})
@RunWith(SpringRunner.class)
public class LogRecordTest {

    @Resource
    private RoundTableService roundTableService;

    @Resource
    private SpecialTopicService specialTopicService;

    @Test
    public void testLogRecord(){

        SpecialTopic specialTopic = new SpecialTopic();
        specialTopic.setSpecialTopicId("111");
        System.out.println(specialTopicService.saveSpecialTopic(specialTopic));

        System.out.println();

        RoundTable roundTable = new RoundTable();
        roundTable.setRoundTableId("222");
        System.out.println(roundTableService.saveRoundTable(roundTable));

        //暂停毫秒
        try { TimeUnit.MILLISECONDS.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }

    }

}