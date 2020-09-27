package com.bonree.netty.test.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Date;

/**
 * All rights Reserved, Designed By www.bonree.com
 *
 * @version V1.0
 * @Title: NettyClient
 * @Package com.bonree.netty.test.netty
 * @Description: NettyClient
 * @author: lihongchao
 * @date: 2019/12/27 15:41
 * @Copyright: 博睿宏远科技发展有限公司 Copyright: Copyright (c) 2007-2019博睿宏远科技发展有限公司,Inc.All
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                });

        Channel channel = bootstrap.connect("127.0.0.1", 8000).channel();

        while (true) {
            String str = new Date() + ": hello world!";
            channel.writeAndFlush(str);
            System.out.println("发送: "+str);
            Thread.sleep(2000);
        }
    }
}





//                     同步阻塞io        伪异步io       非阻塞nio1.4      异步io1.7
//客户端个数-线程          1                   n            1              0
//io类型-阻塞             true              true          false         false
//io类型-异步             true              true          false         false
//api难度                简单              简单           非常复杂        复杂
//调试难度                简单              简单           复杂           复杂
//可靠性                非常差               差             高            高
//吞吐量                低                  中             高            高

