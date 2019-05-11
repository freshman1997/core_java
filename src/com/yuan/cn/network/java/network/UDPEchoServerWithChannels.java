package com.yuan.cn.network.java.network;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @author Crazy
 * @date 2019/2/26
 */
public class UDPEchoServerWithChannels {
    private final static int PORT = 20001;
    // 这里使用足够大的缓冲区，可以保存任何UDP包（UDP包最大的大小就是这么大）
    private final static int MAX_PACKET_SIZE = 65501;

    public static void main(String[] args) {
        try {
            DatagramChannel channel = DatagramChannel.open();
            DatagramSocket socket = channel.socket();
            socket.bind(new InetSocketAddress(PORT));
            ByteBuffer buffer = ByteBuffer.allocateDirect(MAX_PACKET_SIZE);
            while (true)
            {
                SocketAddress client = channel.receive(buffer);
                // 这里如果获取某个字节数据，那么偏移量要改变
                buffer.flip();
                channel.send(buffer, client);
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
