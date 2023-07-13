package com.jiw.dudu.controller;

import com.jiw.dudu.enums.FirmEnum;
import com.jiw.dudu.factory.ShareFirmFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description CompanyQAController
 * @Author pangh
 * @Date 2023年07月13日
 * @Version v1.0.0
 */
@Api(tags = "厂商接口对接")
@RestController
@Slf4j
public class CompanyQAController {

    @ApiOperation("使用策略+工程模式，优化大段if else代码")
    @RequestMapping(value = "/answerCompanyQuestion/{code}/{question}", method = RequestMethod.GET)
    public String answerCompanyQuestion(@PathVariable("code") String code,@PathVariable("question") String question) {
        log.info("厂商调用问答接口，编号：{}、提问内容：{}",code,question);
        FirmEnum firmEnum = FirmEnum.getFirmEnum(code);
        return ShareFirmFactory.getColaStrategy(firmEnum).getKnowledge(question);
    }

}