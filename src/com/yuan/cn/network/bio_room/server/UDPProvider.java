package com.yuan.cn.network.bio_room.server;

import com.yuan.cn.network.bio_room.constant.param.UDPConstants;
import com.yuan.cn.network.bio_room.lib.utils.ByteUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.UUID;

public class UDPProvider {
    private static Provider PROVIDER_INSTANCE;


    public static void start(int port) {
        // 进来先进性检测是否是已经关闭了的
        stop();

        // 使用UUID生成数据，以确保唯一性
        String sn = UUID.randomUUID().toString();

        // 这里开启新的线程来进行处理数据
        Provider provider = new Provider(port, sn);
        // 此处开启线程
        provider.start();

        PROVIDER_INSTANCE = provider;

    }
    public static void stop() {
        if (PROVIDER_INSTANCE != null) {
            PROVIDER_INSTANCE.exit();
            PROVIDER_INSTANCE = null;
        }
    }
    private static class Provider extends Thread
    {
        private final int port;
        private final String sn;
        private boolean done = false;

        private DatagramSocket datagramSocket = null;
        private byte[] buffer = new byte[128];

        private Provider(int port, String sn) {
            this.port = port;
            this.sn = sn;
        }

        @Override
        public void run() {
            System.out.println("===== UDP 提供者开始运行 =====");

            try {
                datagramSocket = new DatagramSocket(UDPConstants.PORT_SERVER);
                // 构建一个全量数据包
                DatagramPacket packet = new DatagramPacket(buffer,  buffer.length);

                try {
                    while (!done) {
                        // 开始接收
                        datagramSocket.receive(packet);
                        int DataLengthOfClient = packet.getLength();
                        byte[] DataOfClient = packet.getData();
                        // 这里的意思是：数据的长度要大于数据的长度 + 命令数据 + 端口信息数据
                        boolean validOrNot = DataLengthOfClient >= (UDPConstants.HEADER.length + 2 + 4)
                                && ByteUtils.startsWith(DataOfClient, UDPConstants.HEADER);

                        System.err.println("新的客户端接入了，接收到的信息： ====> ip:" + packet.getAddress().getHostAddress() + " \t port:" + packet.getPort()
                                + "\t 是否是有效数据：" + validOrNot);


                        if (!validOrNot) {
                            // 数据无效
                            continue;
                        }

                        // 解析命令与回送端口
                        int index = UDPConstants.HEADER.length;
                        // 这里移位 16 是因为short是2个字节的数据，而后面的 & 0xff是因为前面的移位操作，计算机进行自动补全 补1
                        short cmd = (short) ((DataOfClient[index++] << 8) | (DataOfClient[index++] & 0xff));

                        // 下面的和上面的原理一样，只不过端口数据使用的是一个int类型的数据，也就是32位的数据
                        int responsePort = (((DataOfClient[index++]) << 24) |
                                ((DataOfClient[index++] & 0xff) << 16) |
                                ((DataOfClient[index++] & 0xff) << 8) |
                                ((DataOfClient[index] & 0xff)));

                        // 判断合法性
                        if (cmd == 1 && responsePort > 0) {

                            // 构建一份回送数据
                            ByteBuffer cacheBuffer = ByteBuffer.wrap(buffer);
                            cacheBuffer.put(UDPConstants.HEADER);
                            cacheBuffer.putShort((short) 2);
                            cacheBuffer.putInt(port);
                            cacheBuffer.put(sn.getBytes());


                            // 缓冲区的下标
                            int len = cacheBuffer.position();



                            // 直接根据发送者构建一份回送信息
                            DatagramPacket responsePacket = new DatagramPacket(buffer,
                                    len,
                                    packet.getAddress(),
                                    responsePort);
                            datagramSocket.send(responsePacket);
                            System.out.println("UDPProvider response to:" + packet.getAddress().getHostAddress() + "\tport:" + responsePort + "\tdataLen:" + len);
                        } else {
                            System.out.println("UDPProvider receive cmd nonsupport; cmd:" + cmd + "\tport:" + port);
                        }
                    }
                }catch (IOException e)
                {
                    e.printStackTrace();
                }

            } catch (SocketException e) {
                e.printStackTrace();
            }finally {
                close();
            }
            System.out.println("=====UDP 接收并发送数据已经完成=====");
        }
        private void close()
        {
            if (datagramSocket != null) {
                datagramSocket.close();
                datagramSocket = null;
            }
        }

        void exit()
        {
            done = true;
            close();
        }
    }
}
