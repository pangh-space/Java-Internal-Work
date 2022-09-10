package com.jiw.dudu.netty.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description EchoServerHandler
 * @Author pangh
 * @Date 2022年09月09日
 * @Version v1.0.0
 */
@Slf4j
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    private String msg = "哈喽，Netty 客户端...";

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        ByteBuf in = (ByteBuf)o;
        log.info("服务器接收到客户端信息：" + in.toString(CharsetUtil.UTF_8));
        channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer(msg,CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        log.info("服务器接收信息完成...");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        throwable.printStackTrace();
        channelHandlerContext.close();
    }
}