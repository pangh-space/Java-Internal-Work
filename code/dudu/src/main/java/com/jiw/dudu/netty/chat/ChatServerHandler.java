package com.jiw.dudu.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description 服务端Handler
 * @Author pangh
 * @Date 2022年09月14日
 * @Version v1.0.0
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    // 全局事件执行器，单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    // 时间格式化
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 表示 channel处于就绪状态，提示上线
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        //将该客户加入聊天的信息推送给其它在线的客户端
        //该方法会将 channelGroup 中所有的 channel 遍历，并发送消息
        channelGroup.writeAndFlush("[ 客户端 ]" + channel.remoteAddress() + " 上线了 " + dtf.format(LocalDateTime.now()) + "\n");
        // 将当前 channel 加入到 channelGroup
        channelGroup.add(channel);
        System.out.println(channel.remoteAddress() + " 上线了" + "\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        // 将客户端下线消息推送给当前在线客户端
        channelGroup.writeAndFlush("[ 客户端 ]" +channel.remoteAddress() + " 下线了 \n");
        System.out.println(channel.remoteAddress() + " 下线了 \n");
        System.out.println("channelGroup Size=" + channelGroup.size());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) {
        // 获取到当前channel
        Channel channel = channelHandlerContext.channel();
        // 遍历所有channelGroup，根据不同的情况，回送不同的消息
        channelGroup.forEach(ch -> {
            // 判断循环到的channel是否是当前发送消息的channel
            if(channel != ch){
                ch.writeAndFlush("[ 客户端 ]" + channel.remoteAddress() + " 发送了消息：" + s + "\n");
            }else{
                ch.writeAndFlush("[ 自己 ] 发送了消息：" + s + "\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}