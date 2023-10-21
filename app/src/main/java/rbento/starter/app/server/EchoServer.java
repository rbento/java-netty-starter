
/* Copyright (c) 2023 Rodrigo Bento */

package rbento.starter.app.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;
import lombok.SneakyThrows;

/*
 * EchoServerHandler implements the server business logic.
 *
 * This class implements the required steps to bootstrap a server, which includes:
 *
 * 1. Creating a ServerBootstrap instance to configure and bind the server.
 * 2. Creating and assingning and NioEventLoopGroup instance to handle event processing.
 * 3. Specifying the InetSockerAddress to which the server binds.
 * 4. Initialize each new Channel with a n EchoServerHandler instance.
 * 5. Call ServerBootstrap.bind() to bind the server.
 *
 * After these steps the server would be initialized and ready to serve.
 */
public class EchoServer {
    private int port;

    public EchoServer(int port) {
        this.port = port;
    }

    @SneakyThrows
    public void start() {

        /*
         * Is @Sharable so will be reused below.
         */
        final EchoServerHandler serverHandler = new EchoServerHandler();

        /*
         * Creates the EventLoopGroup instance to handle event processing
         * such as accepting new connections and reading/writing data.
         */
        EventLoopGroup group = new NioEventLoopGroup();

        try {

            /*
             * Creates the ServerBootstrap with bootstraps and binds the server.
             */
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap
                    .group(group)

                    /*
                     * Uses NIO transport channel. (Enhanced TCP)
                     *
                     * This is the most widely used transport due to its
                     * scalability and asynchrony.
                     */
                    .channel(NioServerSocketChannel.class)

                    /*
                     * Sets the socket address with the specified port.
                     */
                    .localAddress(new InetSocketAddress(port))

                    /*
                     * When a new connection is accepted, a new child Channel will be created.
                     *
                     * The ChannelInitializer will then add an EchoServerHandler instance
                     *
                     * to the channel's ChannelPipeline, which will be notified of any inbound messages.
                     */
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        @SneakyThrows
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel
                                    .pipeline()

                                    /*
                                     * EchoServerHandler is @Sharable
                                     * so it can be reused here.
                                     */
                                    .addLast(serverHandler);
                        }
                    });

            /*
             * Binds the server asynchronously.
             *
             * sync() waits for the bind to complete.
             */
            ChannelFuture channelFuture = bootstrap.bind().sync();

            /*
             * Gets the closeFuture of the Channel and blocks the current thread until it's complete.
             */
            channelFuture.channel().closeFuture().sync();

        } finally {

            /*
             * Shuts down the EventLoopGroup, releasing all resources.
             */
            group.shutdownGracefully().sync();
        }
    }
}
