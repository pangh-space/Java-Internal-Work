package com.jiw.dudu.design.mediator.computer;

import lombok.Getter;

/**
 * @Description 光驱类，业务方法读光盘内容，将内容读取出来并存入data
 * @Author pangh
 * @Date 2022年10月26日
 * @Version v1.0.0
 */
public class CDDriver extends ComputerColleague {

    //刚开始光驱没碟没内容，否则，放入光盘了开始读取内容，自身状态+业务信息发生了变化
    @Getter
    private String data = "";

    public CDDriver(ComputerMediator computerMediator) {
        super(computerMediator);
    }

    /**
     * 业务方法，readCD，读取光盘里面的数据给上面的data
     */
    public void readCD() {
        this.data = "vcr周杰伦,sound稻香";
        // 关键代码，关键代码，关键代码，关键代码，关键代码，
        // 读取出来的内容有图+有声音，通知主板，光驱系统状态+内容改变了。
        this.getComputerMediator().changed(this);
    }
}