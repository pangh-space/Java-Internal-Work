package com.jiw.dudu.netty.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description EchoClientHandler
 * @Author pangh
 * @Date 2022年09月10日
 * @Version v1.0.0
 */
@Slf4j
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private String msg = "哈喽，Netty 服务器...";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        log.info("客户端接收消息：" + byteBuf.toString(CharsetUtil.UTF_8));
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write(Unpooled.copiedBuffer(
                msg,CharsetUtil.UTF_8));
        ctx.flush();
    }
}