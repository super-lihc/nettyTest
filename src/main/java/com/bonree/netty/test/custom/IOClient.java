package com.bonree.netty.test.custom;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * All rights Reserved, Designed By www.bonree.com
 *
 * @version V1.0
 * @Title: IOClient
 * @Package com.bonree.netty.test
 * @Description: IOClient
 * @author: lihongchao
 * @date: 2019/12/27 10:37
 * @Copyright: 博睿宏远科技发展有限公司 Copyright: Copyright (c) 2007-2019博睿宏远科技发展有限公司,Inc.All
 */
public class IOClient {

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                while (true) {
                    try {
                        String str = new Date() + ": hello world";
                        socket.getOutputStream().write(str.getBytes());
                        socket.getOutputStream().flush();
                        System.out.println("发送: "+str);
                        Thread.sleep(2000);
                    } catch (Exception e) {
                    }
                }
            } catch (IOException e) {
            }
        }).start();
    }
}