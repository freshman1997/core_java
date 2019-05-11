package com.yuan.cn.network.demo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Crazy
 * @date 2019/2/16
 * 服务器端的程序
 */
public class Server {
    public static void main(String[] args) {
        try {
            // 如果本地有多个IP地址，则指定IP地址，否则则在有的可用的查找
            ServerSocket serverSocket = new ServerSocket(2000);

            System.out.println("服务器端准备就绪~");
            System.out.println("服务器端信息："+serverSocket.getInetAddress()+" port:"+serverSocket.getLocalPort());

            for (;;)
            {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandler.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static class ClientHandler extends Thread
    {
        private Socket socket;
        private boolean flag = true;
        ClientHandler(Socket socket)
        {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("新客户端连接了："+socket.getInetAddress()+" port:"+socket.getPort());
            try {
                PrintStream printStream = new PrintStream(socket.getOutputStream());
                BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                do {
                    String s = socketInput.readLine();
                    if("bye".equalsIgnoreCase(s))
                    {
                        flag = false;
                        // 回送
                        printStream.println("bye");
                    }else
                    {
                        System.out.println(s);
                        printStream.println("收到的数据的长度为："+s.length());
                    }
                }while (flag);

            }catch (IOException e)
            {
                e.printStackTrace();
            }finally {
                try {
                    socket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("客户端已关闭："+socket.getInetAddress()+" port:"+socket.getPort());
        }
    }
}
