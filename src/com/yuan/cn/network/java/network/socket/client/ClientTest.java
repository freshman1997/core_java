package com.yuan.cn.network.java.network.socket.client;

import java.io.IOException;

public class ClientTest {
    public static void main(String[] args) throws IOException {
        LongConnectionClient client = new LongConnectionClient(8888, "localhost");
        client.startClient();
    }
}
