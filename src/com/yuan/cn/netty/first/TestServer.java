package com.yuan.cn.netty.first;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class TestServer {
    public static void main(String[] args) throws InterruptedException {
        // 这个是接收请求，然后发送给workerLoop
        EventLoopGroup bossLoop = new NioEventLoopGroup();
        // worker类完成真正的处理
        EventLoopGroup workerLoop = new NioEventLoopGroup();
        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 内部通过反射来创建的
            serverBootstrap.group(bossLoop, workerLoop).channel(NioServerSocketChannel.class)
                    // 请求到来时，自己的handler  如果定义为内部类的话则要定义为static内部类，也就是成为了class级别的
                    .childHandler(new TestServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(1122).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossLoop.shutdownGracefully();
            workerLoop.shutdownGracefully();
        }
    }

    private static class TestServerInitializer extends ChannelInitializer<SocketChannel>{

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            // 这些对象不要是单例的
            pipeline.addLast("httpServerCodec", new HttpServerCodec());
            pipeline.addLast("testHttpServerHandler", new TestHttpServerHandler());
        }
    }
    private static class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject>{

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
            if(msg instanceof HttpRequest) {
                ByteBuf content = Unpooled.copiedBuffer("Hello World!", CharsetUtil.UTF_8);
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

                ctx.writeAndFlush(response);
            }
        }
    }
}
