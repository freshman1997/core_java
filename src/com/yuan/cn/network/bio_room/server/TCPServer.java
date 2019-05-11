package com.yuan.cn.network.bio_room.server;

import com.yuan.cn.network.bio_room.server.handler.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer implements ClientHandler.ClientHandlerCallback{
    private final int port;
    private TCPServer.ClientListener mListener;
    // 客户端的列表
    private List<ClientHandler> clientList = new ArrayList<>();

    private final ExecutorService forwardingThreadExecutor;

    public boolean start() {
        try {
            ClientListener listener = new ClientListener(port);
            mListener = listener;
            listener.start();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 这是服务器端发送给每个客户端的函数，也就是和推送一样的操作
    public synchronized void broadcast(String str) {
        for (ClientHandler clientHandler : clientList) {
            clientHandler.send(str);
        }
    }

    public void stop() {
        if (mListener != null) {
            mListener.exit();
        }

        // 在进行关闭操作时不能操作这个主类，直到关闭完客户端
        // 保证遍历的安全
        synchronized (TCPServer.this) {
            // 遍历关闭  关闭读写和socket
            // 但是并没有关闭服务器端的套接字，也就是说还可以接收客户端的连接
            for (ClientHandler clientHandler : clientList) {
                clientHandler.exit();
            }

            // 清空列表
            clientList.clear();
        }
        forwardingThreadExecutor.shutdownNow();
    }
    public TCPServer(int port) {
        forwardingThreadExecutor = Executors.newSingleThreadExecutor();
        this.port = port;
    }

    @Override
    public void onSelfClosed(ClientHandler handler) {
        // 关闭或断开一个客户端连接则移除它
        clientList.remove(handler);
    }

    @Override
    public void onNewMessageArrived(ClientHandler handler, String msg) {
        // 打印到控制台
        System.out.println("Received-" + handler.getClientInfo() + ":" + msg);
        forwardingThreadExecutor.execute(()->{
            synchronized (TCPServer.this) {
                for (ClientHandler chandler : clientList) {
                    if(chandler.equals(handler))
                    {
                        continue;
                    }
                    chandler.send(msg);
                }
            }
        });
    }

    private class ClientListener extends Thread{
        private final  ServerSocket server;
        private boolean done = false;

        private ClientListener(int port) throws IOException {
            server = new ServerSocket(port);
            System.out.println("服务器信息：" + server.getInetAddress() + " P:" + server.getLocalPort());
        }

        @Override
        public void run() {
            System.out.println("TCP 服务器端开始进行阻塞等待连接。");
            Socket client = null;
            //得到客户端
            while (!done) {
                try {
                    client = server.accept();

                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
                try {
                    // 客户端构建异步线程 处理数据
                    ClientHandler clientHandler = new ClientHandler(client, TCPServer.this);
                    // 读取数据并打印 这里起始就是开启了读取操作的线程，打印是在线程读取到数据的情况下才会发生的
                    clientHandler.readToPrint();

                    // 添加同步处理
                    // 也就是当获取到了客户端之后才可以进行获取下一个客户端的连接，一次只能连接一个连接，不能同时多个客户端的接入
                    synchronized (TCPServer.this) {
                        clientList.add(clientHandler);
                    }
                } catch (IOException e) {
                    System.out.println("客户端连接异常：" + e.getMessage());
                }
            }
            System.out.println("服务器已关闭！");
        }
        void exit() {
            done = true;
            try {
                // 直接把套接字关闭，也就是把底层的TCP关闭了
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
