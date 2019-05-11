package com.yuan.cn.network.demo2;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author Crazy
 * @date 2019/2/17
 * 这个UDP的流程是：
 *  1、创建一个DataGramSocket对象
 *  2、构建发送的实体数据（byte数组）
 *  3、使用DatagramPacket对象包装数据
 *  4、发送DatagramSocket发送DatagramPacket数据包
 */
public class Searcher {
    private static final int LISTEN_PORT = 30000;
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("UDP Searcher sendBroadcast started.");

        Listener listener =listen();
        sendBroadcast();
        System.in.read();
        List<Device> devices = listener.getDevicesAndClose();
        for (Device device : devices)
        {
            System.out.println("Device :"+device.toString());
        }
        System.out.println("UDP Searcher finished");
    }
    private static Listener listen() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Listener listener = new Listener(LISTEN_PORT, countDownLatch);
        listener.start();
        // 等待完成
        countDownLatch.await();
        return listener;
    }
    public static void sendBroadcast() throws IOException {

        DatagramSocket datagramSocket = new DatagramSocket();
        String requestData = MessageCreator.buildWithPort(LISTEN_PORT);
        byte[] requestBytes = requestData.getBytes();
        DatagramPacket packet = new DatagramPacket(requestBytes, requestBytes.length);

        // 20000 端口，广播的地址
        packet.setAddress(InetAddress.getByName("255.255.255.255"));
        packet.setPort(20000);
        datagramSocket.send(packet);

        datagramSocket.close();

        System.out.println("UDPSearcher sendBroadcast finished.");
    }
    // 设备信息的类
    private static class Device
    {
        final int port;
        final String ip;
        final String sn;

        private Device(int port, String ip, String sn) {
            this.port = port;
            this.ip = ip;
            this.sn = sn;
        }

        @Override
        public String toString() {
            return "Device{" +
                    "port=" + port +
                    ", ip='" + ip + '\'' +
                    ", sn='" + sn + '\'' +
                    '}';
        }
    }
    private static class Listener extends Thread
    {
        private final int listenPort;
        private final CountDownLatch countDownLatch;
        private final List<Device> devices = new ArrayList<>();
        private boolean done = false;
        private DatagramSocket ds = null;

        private Listener(int listenPort, CountDownLatch countDownLatch) {
            this.listenPort = listenPort;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            super.run();
            // 计数减一，通知已启动
            countDownLatch.countDown();
            try {
                // 监听回送端口
                ds = new DatagramSocket(listenPort);
                while (!done)
                {
                    // 构建接收实体
                    String msg = "hello world";
                    final byte[] buffer = new byte[512];
                    DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);

                    // 接收
                    ds.receive(receivedPacket);

                    // 打印接收到的信息和发送者的信息
                    // 发送者的IP地址
                    String ip = receivedPacket.getAddress().getHostAddress();
                    int port = receivedPacket.getPort();
                    int dataLen = receivedPacket.getLength();
                    String data = new String(receivedPacket.getData(),0, dataLen,"UTF-8");
                    System.out.println("Searcher receive from IP :"+ip+"\tport:"+port+"\t receive data:"+data);

                    String sn = MessageCreator.parseSn(data);
                    // 如果解析的sn正确的话，则上面获取的信息也是正确的，就可以放到设备信息类中
                    if(sn!=null)
                    {
                        Device device = new Device(port, ip, sn);
                        devices.add(device);

                    }
                }
            }catch (Exception e)
            {

            }finally {
                close();
            }
            System.out.println("UDP Searcher listener is finished.");
        }
        List<Device> getDevicesAndClose()
        {
            done = true;
            close();
            return devices;
        }
        void close()
        {
            if(ds != null)
            {
                ds.close();
                ds = null;
            }
        }
    }
}
