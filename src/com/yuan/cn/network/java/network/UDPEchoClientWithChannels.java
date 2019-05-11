package com.yuan.cn.network.java.network;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;
public class UDPEchoClientWithChannels {
    private final static int PORT = 20001;
    private final static int LIMIT = 100;

    public static void main(String[] args) {
        SocketAddress remote = null;
        try {
            remote = new InetSocketAddress(Inet4Address.getLocalHost(), PORT);
        } catch (UnknownHostException e) {
            System.out.println("there is no InetSocketAddress available!");
        }
        // 1、打开一个通道
        // 2、设置是否是阻塞的
        // 3、设置好地址并连接
        // 4、打开多路复用Selector
        // 5、为通道注册options
        // 6、分配缓冲区
        // 7、开始进行网络操作
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            channel.connect(remote);
            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int n = 0;
            int numberRead = 0;
            while (true) {
                if (numberRead == LIMIT) break;
                // 为一个连接等待一分钟
                selector.select(60000);
                Set<SelectionKey> readyKeys = selector.keys();
                if (readyKeys.isEmpty() && n == LIMIT) {
                    break;
                } else {
                    Iterator<SelectionKey> it = readyKeys.iterator();
                    // 此处开始遍历，遇到读则进行读操作，遇到写则进行写操作
                    while (it.hasNext()) {
                        SelectionKey next = it.next();
                        // it.remove();
                        if (next.isReadable()) {
                            // 在进行操作之前，先清空缓冲区
                            buffer.clear();
                            channel.read(buffer);
                            // 定位到头部
                            buffer.flip();
                            String echo = new String(buffer.array(),0, buffer.limit());
                            System.out.println("Read :" + echo);
                            numberRead++;
                        }
                        if (next.isWritable()) {
                            buffer.clear();
                            buffer.put("你好！".getBytes());
                            buffer.flip();
                            channel.write(buffer);
                            System.out.println("Wrote :" + n);
                            n++;
                            if (n == LIMIT) {
                                // 所有包已写入，切换到只读模式
                                next.interestOps(SelectionKey.OP_READ);
                            }
                        }
                    }
                }
            }
            System.out.println("Echoed " + numberRead + " out of " + LIMIT + " sent.");
            System.out.println("Success rate :" + 100.0 * numberRead / LIMIT + "%");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
