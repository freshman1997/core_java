package com.yuan.cn.network.demo1;

import java.io.*;
import java.net.*;

/**
 * @author Crazy
 * @date 2019/2/16
 * 这是TCP/IP 的socket客户端程序
 */
public class Client {

    public static void main(String[] args) {
        Socket socket = new Socket();

        try {
            socket.setSoTimeout(2000);

            socket.connect(new InetSocketAddress(InetAddress.getLocalHost(),2000),3000);

            System.out.println("已发起服务器连接，并进入后续流程~");
            System.out.println("客户端信息："+socket.getLocalSocketAddress()+" port:"+socket.getLocalPort());
            System.out.println("服务器端信息："+socket.getInetAddress()+" port:"+socket.getPort());

            toDo(socket);

            socket.close();
            System.out.println("客户端已退出。。。。。。");
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void toDo(Socket client) throws IOException
    {
        // 构建键盘输入流
        InputStream in = System.in;
        BufferedReader input = new BufferedReader(new InputStreamReader(in));
        // 获取客户端的输出流并转换为PrintStream
        OutputStream outputStream = client.getOutputStream();
        PrintStream socketPrintStream = new PrintStream(outputStream);
        // 获取返回的输入流（也就是响应的数据流）并转换为BufferedReader
        BufferedReader socketInputStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
        boolean flag = true;
        do {
            // 从键盘读取一行
            System.out.println("请输入数据：");
            String str = input.readLine();
            socketPrintStream.println(str);
            // 这里时从服务器的输入流中读取一行
            String echo = socketInputStream.readLine();
            if ("bye".equalsIgnoreCase(echo)) {
                flag = false;
            }else
            {
                System.out.println(echo);
            }
        }while (flag);

        input.close();
        socketPrintStream.close();
        socketInputStream.close();
    }
}
