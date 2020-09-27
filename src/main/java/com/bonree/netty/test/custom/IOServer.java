package com.bonree.netty.test.custom;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * All rights Reserved, Designed By www.bonree.com
 *
 * @version V1.0
 * @Title: IOServer
 * @Package com.bonree.netty.test
 * @Description: IOServer
 * @author: lihongchao
 * @date: 2019/12/27 10:37
 * @Copyright: 博睿宏远科技发展有限公司 Copyright: Copyright (c) 2007-2019博睿宏远科技发展有限公司,Inc.All
 */
public class IOServer {
    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(8000);

        // (1) 接收新连接线程
        new Thread(() -> {
            while (true) {
                try {
                    // (1) 阻塞方法获取新的连接
                    Socket socket = serverSocket.accept();

                    // (2) 每一个新的连接都创建一个线程，负责读取数据
                    new Thread(() -> {
                        try {
                            byte[] data = new byte[1024];
                            InputStream inputStream = socket.getInputStream();
                            while (true) {
                                int len;
                                // (3) 按字节流方式读取数据
                                while ((len = inputStream.read(data)) != -1) {
                                    System.out.println("接收"+new String(data, 0, len));
                                }
                            }
                        } catch (IOException e) {
                        }
                    }).start();

                } catch (IOException e) {
                }

            }
        }).start();
    }
}







//IO编程模型在客户端较少的情况下运行良好，但是对于客户端比较多的业务来说，
// 单机服务端可能需要支撑成千上万的连接，IO模型可能就不太合适了，我们来分析一下原因。
//
//上面的demo，从服务端代码中我们可以看到，在传统的IO模型中，每个连接创建成功之后都需要一个线程来维护，
// 每个线程包含一个while死循环，
// .那么1w个连接对应1w个线程，继而1w个while死循环，这就带来如下几个问题：
//
//线程资源受限：线程是操作系统中非常宝贵的资源，同一时刻有大量的线程处于阻塞状态是非常严重的资源浪费，操作系统耗不起
//线程切换效率低下：单机cpu核数固定，线程爆炸之后操作系统频繁进行线程切换，应用性能急剧下降。
//除了以上两个问题，IO编程中，我们看到数据读写是以字节流为单位，效率不高。
//为了解决这三个问题，JDK在1.4之后提出了NIO。
