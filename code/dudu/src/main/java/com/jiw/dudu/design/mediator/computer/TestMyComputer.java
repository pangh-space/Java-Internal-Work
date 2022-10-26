package com.jiw.dudu.design.mediator.computer;

/**
 * @Description 中介者模式测试验证
 * @Author pangh
 * @Date 2022年10月26日
 * @Version v1.0.0
 */
public class TestMyComputer {

    public static void main(String[] args) {
        MainBoard mainBoard = new MainBoard();

        CDDriver cdDriver = new CDDriver(mainBoard);
        Cpu cpu = new Cpu(mainBoard);
        VideoCard videoCard = new VideoCard(mainBoard);
        SoundCard soundCard = new SoundCard(mainBoard);

        mainBoard.setCdDriver(cdDriver);
        mainBoard.setCpu(cpu);
        mainBoard.setVideoCard(videoCard);
        mainBoard.setSoundCard(soundCard);

        //故事从光驱读取CD开始交互流转，类似电商系统客户下订单后开始各个子系统的配合
        cdDriver.readCD();
    }

}