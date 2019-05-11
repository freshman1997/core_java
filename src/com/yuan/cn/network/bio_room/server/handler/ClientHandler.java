package com.yuan.cn.network.bio_room.server.handler;

import com.yuan.cn.network.demo4.clink.utils.CloseUtils;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler {
    private final Socket socket;
    private final ClientReadHandler readHandler;
    private final ClientHandler.ClientWriteHandler writeHandler;
    private final ClientHandlerCallback clientHandlerCallback;
    private final String clientInfo;

    // 这里传入的socket只为了可以得到客户端的输入输出流，然后进行过数据处理
    public ClientHandler(Socket socket, ClientHandlerCallback clientHandlerCallback) throws IOException {
        this.socket = socket;
        this.clientHandlerCallback = clientHandlerCallback;
        this.readHandler = new ClientReadHandler(socket.getInputStream());
        this.writeHandler = new ClientWriteHandler(new PrintStream(socket.getOutputStream()));
        clientInfo = "新的客户端接入进来了 ===> ip地址为：" + socket.getInetAddress() + "\t 端口为：" + socket.getPort();
        System.out.println(clientInfo);
    }

    public String getClientInfo() {
        return this.clientInfo;
    }

    // 创建线程使用异步处理发送数据操作
    public void send(String str) {
        writeHandler.send(str);
    }

    // 创建线程异步处理数据读取操作
    public void readToPrint() {
        readHandler.start();
    }

    // 关闭自身
    private void exitBySelf() {
        // 关闭socket
        exit();
        clientHandlerCallback.onSelfClosed(this);
    }

    public interface ClientHandlerCallback {
        // 自身关闭通知
        void onSelfClosed(ClientHandler handler);

        // 收到消息时通知
        void onNewMessageArrived(ClientHandler handler, String msg);
    }

    public void exit() {
        readHandler.exit();
        writeHandler.exit();
        CloseUtils.close(socket);
        System.out.println("客户端已退出：" + socket.getInetAddress() +
                " P:" + socket.getPort());
    }

    class ClientReadHandler extends Thread {
        private final InputStream inputStream;
        // 表示支持结束的  默认为false
        private boolean done = false;

        ClientReadHandler(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        void exit() {
            done = true;
            CloseUtils.close(inputStream);
        }

        @Override
        public void run() {

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            try {
                do {
                    final String msg = reader.readLine();
                    if (msg == null) {
                        System.out.println("客户端已经无法读取数据！");
                        ClientHandler.this.exitBySelf();
                        break;
                    }
                    clientHandlerCallback.onNewMessageArrived(ClientHandler.this, msg);

                } while (!done);
            } catch (IOException e) {
                e.printStackTrace();
                if (!done) {
                    System.out.println("连接异常断开");
                    ClientHandler.this.exitBySelf();
                }
            } finally {
                // 连接关闭
                CloseUtils.close(inputStream);
            }


        }
    }

    class ClientWriteHandler{
        // 表示是支持退出操作的
        private boolean done = false;
        private final PrintStream printer;
        private final ExecutorService executorService;
        void send(String msg) {
            if(done)
            {
                return;
            }
            executorService.execute(new WriterRunnable(msg));
        }

        ClientWriteHandler(PrintStream printer) {
            this.printer = printer;
            // 单线程线程池
            executorService = Executors.newSingleThreadExecutor();
        }
        class WriterRunnable implements Runnable
        {
            private final String msg;

            WriterRunnable(String msg) {
                this.msg = msg;
            }

            @Override
            public void run() {
                // 如果当前的 ClientWriteHandler 的done为true则直接返回
                if (ClientWriteHandler.this.done) {
                    return;
                }
                try {
                    ClientWriteHandler.this.printer.println(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        void exit() {
            done = true;
            CloseUtils.close(printer);
            executorService.shutdownNow();
        }
    }
}
