package com.yuan.cn.network.bio_room.lib.utils;

public class ByteUtils {
    /**
     * 这是比较两个byte数组的函数的起始部分
     * @param source
     * @param match
     * @return
     */
    public static boolean startsWith(byte[] source, byte[] match) {
        return startsWith(source, 0, match);
    }

    /**
     * 比较两个byte数组的起始部分，从指定的偏移开始比较对应的位置
     * @param source
     * @param offset
     * @param match
     * @return
     */
    public static boolean startsWith(byte[] source, int offset, byte[] match) {

        if (match.length > (source.length - offset)) {
            return false;
        }

        // 遍历
        for (int i = 0; i < match.length; i++) {
            if (source[offset + i] != match[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 比较两个字节数组的内容是否相等，需要遍历进行比较
     * @param source
     * @param match
     * @return
     */
    public static boolean equals(byte[] source, byte[] match) {

        if (match.length != source.length) {
            return false;
        }
        return startsWith(source, 0, match);
    }

    /**
     * 复制字节数组的函数，调用系统的System.arraycopy()复制到对应的字节数组中
     * @param source        源字节数组
     * @param srcBegin      起始下标
     * @param srcEnd        结束下标
     * @param destination   目标字节数组
     * @param dstBegin      目标字节数组的起始下标
     */
    public static void getBytes(byte[] source, int srcBegin, int srcEnd, byte[] destination,
                                int dstBegin) {
        // 传入源字节数组、目标字节数组、源数组起始下标、结束下标、目标字节数组下标、以及复制的长度，即源字节数组的结束下标-起始下标
        System.arraycopy(source, srcBegin, destination, dstBegin, srcEnd - srcBegin);
    }

    /**
     * 截取字节数组
     * @param source
     * @param srcBegin
     * @param srcEnd
     * @return
     */
    public static byte[] subbytes(byte[] source, int srcBegin, int srcEnd) {
        byte destination[];

        destination = new byte[srcEnd - srcBegin];
        getBytes(source, srcBegin, srcEnd, destination, 0);

        return destination;
    }

    /**
     * 截取字节数组，指定起始下标到该字节数组的长度为止
     * @param source
     * @param srcBegin
     * @return
     */
    public static byte[] subbytes(byte[] source, int srcBegin) {
        return subbytes(source, srcBegin, source.length);
    }
}
