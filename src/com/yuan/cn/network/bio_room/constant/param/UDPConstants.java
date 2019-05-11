package com.yuan.cn.network.bio_room.constant.param;

public class UDPConstants {
    // 公用头部 8 位
    public static byte[] HEADER = new byte[]{7, 7, 7, 7, 7, 7, 7, 7};
    // 服务求固化的UDP接收端口
    public static int PORT_SERVER = 30201;
    // 客户端回送的端口
    public static int PORT_CLIENT_RESPONSE = 30202;
}
