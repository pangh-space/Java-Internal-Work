package com.jiw.dudu.design.mediator.computer;

/**
 * @Description ComputerMediator
 * @Author pangh
 * @Date 2022年10月26日
 * @Version v1.0.0
 */
public interface ComputerMediator {

    /**
     * 变化后会通知
     *
     * @param computerColleague
     */
    public void changed(ComputerColleague computerColleague);

}