package com.jiw.dudu.design.mediator.computer;

import lombok.Getter;

/**
 * @Description ComputerColleague
 * @Author pangh
 * @Date 2022年10月26日
 * @Version v1.0.0
 *
 * 1. 所有电脑耗材的配件总父类
 * 2. 它后面有多个子系统，全部插入主板上，所以，构造注入interface ComputerMediator
 */
public abstract class ComputerColleague {

    /**
     * 持有中介者对象
     */
    @Getter
    private ComputerMediator computerMediator;

    public ComputerColleague(ComputerMediator computerMediator){
        this.computerMediator = computerMediator;
    }

}