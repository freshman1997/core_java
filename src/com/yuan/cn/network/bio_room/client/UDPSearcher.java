package com.yuan.cn.network.bio_room.client;

import com.yuan.cn.network.demo4.clink.utils.ByteUtils;
import com.yuan.cn.network.bio_room.client.bean.ServerInfo;
import com.yuan.cn.network.bio_room.constant.param.UDPConstants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class UDPSearcher {

    private static final int LISTEN_PORT = UDPConstants.PORT_CLIENT_RESPONSE;
    public static ServerInfo getServerInfo(int timeout)
    {
        System.out.println("=====开始获取服务器信息=====");
        CountDownLatch receiveFlag = new CountDownLatch(1);
        Listener listener = null;
        try {
            listener = listen(receiveFlag);

            sendBroadcast();
            receiveFlag.await(timeout, TimeUnit.MILLISECONDS);

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("======接收完成======");
        if(listener == null)
        {
            return null;
        }
        List<ServerInfo> serverInfoList = listener.getServerAndClose();
        if(serverInfoList.size() > 0)
        {
            return  serverInfoList.get(0);
        }
        return null;
    }
    private static Listener listen(CountDownLatch receiveLatch) throws InterruptedException {
        System.out.println("======UDP搜索开始了======.");

        CountDownLatch startDownLatch = new CountDownLatch(1);
        Listener listener = new Listener(LISTEN_PORT, startDownLatch, receiveLatch);
        listener.start();
        startDownLatch.await();
        return listener;
    }

    private static void sendBroadcast() throws IOException {
        System.out.println("=======UDP搜索者开始发送广播并等待回应=======");

        // 作为搜索方，让系统自动分配端口
        DatagramSocket ds = new DatagramSocket();

        // 构建一份请求数据
        ByteBuffer byteBuffer = ByteBuffer.allocate(128);

        // 头部
        byteBuffer.put(UDPConstants.HEADER);
        // CMD命名
        byteBuffer.putShort((short) 1);
        // 回送端口信息
        byteBuffer.putInt(LISTEN_PORT);

        // 直接构建packet
        DatagramPacket requestPacket = new DatagramPacket(byteBuffer.array(),

                byteBuffer.position() + 1);

        // 广播地址
        requestPacket.setAddress(InetAddress.getByName("255.255.255.255"));
        // 设置服务器端口
        requestPacket.setPort(UDPConstants.PORT_SERVER);

        // 发送
        ds.send(requestPacket);
        ds.close();

        // 完成
        System.out.println("======UDP搜索已经结束======");
    }


    private static class Listener extends Thread
    {
        private final int listenPort;
        private final CountDownLatch startDownLatch;
        private final CountDownLatch receiveDownLatch;
        private final List<ServerInfo> serverInfoList = new ArrayList<>();
        private final byte[] buffer = new byte[128];
        private final int minLen = UDPConstants.HEADER.length + 2 + 4;
        private boolean done = false;
        private DatagramSocket ds = null;

        private Listener(int listenPort, CountDownLatch startDownLatch, CountDownLatch receiveDownLatch) {
            this.listenPort = listenPort;
            this.startDownLatch = startDownLatch;
            this.receiveDownLatch = receiveDownLatch;
        }

        @Override
        public void run() {

            // 取消阻塞，开启任务
            startDownLatch.countDown();

            try {

                ds = new DatagramSocket(listenPort);

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                while (!done){
                    ds.receive(packet);

                    byte[] data = packet.getData();
                    int port = packet.getPort();
                    String ip = packet.getAddress().getHostAddress();
                    int dataLength = packet.getLength();

                    boolean isValid = dataLength >= minLen
                            && ByteUtils.startsWith(data, UDPConstants.HEADER);

                    System.out.println("当前的服务器的IP地址为：" + ip + "服务器上的端口为：" + port + " 收到的数据长度为：" + dataLength);

                    if (!isValid) {
                        // 数据无效则继续（接收数据）
                        continue;
                    }

                    ByteBuffer byteBuffer = ByteBuffer.wrap(data, UDPConstants.HEADER.length, dataLength);
                    final short cmd = byteBuffer.getShort();
                    final int serverPort = byteBuffer.getInt();

                    if (cmd != 2 || serverPort <= 0) {
                        System.out.println("UDPSearcher receive cmd:" + cmd + "\tserverPort:" + serverPort);
                        continue;
                    }

                    String sn = new String(buffer, minLen, dataLength - minLen);
                    ServerInfo serverInfo = new ServerInfo(sn, serverPort, ip);

                    serverInfoList.add(serverInfo);
                    // 成功接收到一份
                    receiveDownLatch.countDown();
                }
            } catch (IOException e) {
            }
            System.out.println("UDPSearcher listener finished.");
        }
        private void close() {
            if (ds != null) {
                ds.close();
                ds = null;
            }
        }
        List<ServerInfo> getServerAndClose() {
            done = true;
            close();
            return serverInfoList;
        }
    }
}
