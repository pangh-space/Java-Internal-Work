package com.jiw.dudu.design.mediator.computer;

/**
 * @Description VideoCard
 * @Author pangh
 * @Date 2022年10月26日
 * @Version v1.0.0
 */
public class VideoCard extends ComputerColleague {
    public VideoCard(ComputerMediator computerMediator) {
        super(computerMediator);
    }

    public void showVideoData(String data) {
        System.out.println("----你看的video：" + data);
    }
}