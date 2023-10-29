
/* Copyright (c) 2023 Rodrigo Bento */

package rbento.starter.app.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Implements EchoClient business logic.
 */
@Slf4j
@ChannelHandler.Sharable // This class' instances can be safely shared among channels.
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        /*
         * When notified that the channel is active, sends a message.
         */
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty Rocks!", CharsetUtil.UTF_8));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
        /*
         * Logs a dump of the received message.
         */
        log.info("[CLIENT] Received: {}", msg.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        /*
         * Logs the error, if any, and closes the channel.
         */
        log.error("[CLIENT]", cause);
        ctx.close();
    }
}
