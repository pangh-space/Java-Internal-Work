package com.jiw.dudu.design.mediator.computer;

import lombok.Getter;

/**
 * @Description 电脑CPU
 * @Author pangh
 * @Date 2022年10月26日
 * @Version v1.0.0
 */
public class Cpu extends ComputerColleague {

    @Getter
    private String videoData = "";
    @Getter
    private String soundData = "";

    public Cpu(ComputerMediator computerMediator) {
        super(computerMediator);
    }

    /**
     * cpu系统的上家是光驱，cpu需要处理图像vcr+声音sound，我没有从光驱系统拿，
     * 解耦了直接从主板系统中介者处获取，cpu系统里面有光驱系统吗？无
     *
     * @param data
     */
    public void executeData(String data) {
        String[] split = data.split(",");

        this.videoData = split[0];
        this.soundData = split[1];

        this.getComputerMediator().changed(this);
    }

}