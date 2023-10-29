
/* Copyright (c) 2023 Rodrigo Bento */

package rbento.starter.app.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;
import lombok.SneakyThrows;

public class EchoClient {

    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @SneakyThrows
    public void start() {
        /*
         * Specifies the event loop group to handle client events.
         */
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap
                    .group(group)
                    /*
                     * Uses NIO channel type for enchanced TCP transport.
                     */
                    .channel(NioSocketChannel.class)
                    /*
                     * Sets the target server InetSocketAddress.
                     * Client and server could use different transport, ex: OIO vs NIO
                     */
                    .remoteAddress(new InetSocketAddress(host, port))
                    /*
                     * Adds an EchoClientHandler to the pipeline when a channel is created.
                     */
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            /*
             * Connects to the remote peer. Waits until the connect completes.
             */
            ChannelFuture future = bootstrap.connect().sync();

            /*
             * Blocks until the channel closes.
             */
            future.channel().closeFuture().sync();
        } finally {

            /*
             * Shuts down the group thread pool and release all resources.
             */
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 100; i++) {

            new EchoClient("localhost", 3000).start();

            Thread.sleep(8000);
        }
    }
}
