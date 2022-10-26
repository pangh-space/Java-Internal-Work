package com.jiw.dudu.design.mediator.computer;

/**
 * @Description 声卡设备
 * @Author pangh
 * @Date 2022年10月26日
 * @Version v1.0.0
 */
public class SoundCard extends ComputerColleague {

    public SoundCard(ComputerMediator computerMediator) {
        super(computerMediator);
    }

    public void showSoundData(String data) {
        System.out.println("----你听的sound: " + data);
    }
}