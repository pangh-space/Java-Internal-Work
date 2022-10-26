package com.jiw.dudu.design.mediator.computer;

import lombok.Setter;

/**
 * @Description 电脑主板
 * @Author pangh
 * @Date 2022年10月26日
 * @Version v1.0.0
 */
public class MainBoard implements ComputerMediator {

    @Setter private CDDriver cdDriver;
    @Setter private Cpu cpu;
    @Setter private VideoCard videoCard;
    @Setter private SoundCard soundCard;

    private void openCDDriver_ReadData(CDDriver cd){
        String data = cd.getData();
        this.cpu.executeData(data);
    }

    private void openCPU(Cpu cpu){
        String videoData = cpu.getVideoData();
        String soundData = cpu.getSoundData();
        this.videoCard.showVideoData(videoData);
        this.soundCard.showSoundData(soundData);
    }

    /**
     * 变化后会通知
     *
     * @param computerColleague
     */
    @Override
    public void changed(ComputerColleague computerColleague) {
        if(computerColleague == cdDriver){
            this.openCDDriver_ReadData((CDDriver) computerColleague);
            /**
             * qita 草操作
             *
             */
        }else if(computerColleague == cpu){
            this.openCPU((Cpu) computerColleague);
        }
    }
}