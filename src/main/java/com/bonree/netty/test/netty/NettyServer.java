package com.bonree.netty.test.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * All rights Reserved, Designed By www.bonree.com
 *
 * @version V1.0
 * @Title: NettyServer
 * @Package com.bonree.netty.test.netty
 * @Description: NettyServer
 * @author: lihongchao
 * @date: 2019/12/27 15:32
 * @Copyright: 博睿宏远科技发展有限公司 Copyright: Copyright (c) 2007-2019博睿宏远科技发展有限公司,Inc.All
 */

public class NettyServer {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //boos对应，IOServer.java中的接受新连接线程，主要负责创建新连接
        //worker对应 IOServer.java中的负责读取数据的线程，主要用于读取数据以及业务逻辑处理
        //NioEventLoopGroup其实就是一个实现了Java ExecutorService的线程池，
        // 其中的线程数可定制，若不设置线程数参数，则该参数值默认为2 * CPU核数量，
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        OioEventLoopGroup bossGroupO = new OioEventLoopGroup();
        OioEventLoopGroup workerGroupO = new OioEventLoopGroup();
        serverBootstrap
                //指定线程模型 连接建立完成以后由 主线程 对应的acceptor将后续的数据处理分发给 子反应组 （subReactor）进行处理
                .group(bossGroup, workerGroup)
                //指定io模型 nio是 NioServerSocketChannel   BIO是 OioServerSocketChannel
                .channel(NioServerSocketChannel.class)
                .handler(new ChannelInboundHandlerAdapter(){

                })
                //在ServerBootstrap的初始化过程中，会为其添加一个实现了acceptor机制的Handler 这里即ChannelInitializer
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                                System.out.println(msg);
                            }
                        });
                    }
                })
                .bind(8000);

//        serverBootstrap.bind(8000).addListener(new GenericFutureListener<Future<? super Void>>() {
//            public void operationComplete(Future<? super Void> future) {
//                if (future.isSuccess()) {
//                    System.out.println("端口绑定成功!");
//                } else {
//                    System.err.println("端口绑定失败!");
//                }
//            }
//        });

    }
}


//      NIO模型的缺点，这种模型由于IO在阻塞时会一直等待，因此在用户负载增加时，性能下降的非常快。server导致阻塞的原因有：
//
//        1、serversocket的accept方法，阻塞等待client连接，直到client连接成功。
//
//        2、线程从socket inputstream读入数据，会进入阻塞状态，直到全部数据读完。
//
//        3、线程向socket outputstream写入数据，会阻塞直到全部数据写完