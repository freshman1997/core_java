package com.yuan.cn.network.demo2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.UUID;

/**
 * @author Crazy
 * @date 2019/2/16
 * 这是UDP的提供者
 * UDP 既可以发送消息也可以接收消息
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        String sn = UUID.randomUUID().toString();
        ProviderHandler handler = new ProviderHandler(sn);
        handler.start();

        // 读取任意键盘信息后可以退出
        System.in.read();
        handler.exit();

    }

    private static class ProviderHandler extends Thread {
        private final String sn;
        private boolean done = false;
        private DatagramSocket ds = null;

        private ProviderHandler(String sn) {
            this.sn = sn;
        }

        @Override
        public void run() {
            super.run();
            System.out.println("UDP 提供者已经开始进行---");
            // 作为一个接收者，指定一个端口用于数据接收
            try {
                ds = new DatagramSocket(20000);

                while (!done) {
                    // 构建接收实体
                    final byte[] buf = new byte[2048];
                    DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);

                    ds.receive(receivePacket);

                    // 打印接收到的信息和发送者的信息
                    // 发送者的IP地址
                    String ip = receivePacket.getAddress().getHostAddress();
                    int port = receivePacket.getPort();
                    int dataLen = receivePacket.getLength();
                    String data = new String(receivePacket.getData(), 0, dataLen);
                    System.out.println("Provider receive from IP :" + ip + "\tport:" + port + "\tdataLength:" + dataLen + "\t receive data :" + data);

                    int responsePort = MessageCreator.parsePort(data);
                    System.out.println(responsePort);
                    if(responsePort != -1)
                    {
                        String responseData = MessageCreator.buildWithSn(sn);
                        System.out.println(responseData);
                        byte[] responseDataBytes = responseData.getBytes();

                        DatagramPacket responsePacket = new DatagramPacket(responseDataBytes, responseData.length(),
                                receivePacket.getAddress(), responsePort);
                        ds.send(responsePacket);
                    }
                }
            }catch (SocketException e)
            {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Provider Finished.");
        }
        void exit() {
            done = true;
            close();
        }

        private void close() {
            if (ds != null) {
                ds.close();
                ds = null;
            }

        }
    }
}
