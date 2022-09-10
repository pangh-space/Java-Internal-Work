package com.jiw.dudu.listener;

import com.jiw.dudu.netty.base.EchoServer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Description SrpingBoot应用启动成功监听器
 * @Author pangh
 * @Date 2022年09月10日
 * @Version v1.0.0
 */
@Component
public class NettyStartListener implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 监听即启动成功后，启动Netty服务端
//        new EchoServer(9999).start();
    }
}