package com.yuan.cn.hotswap;

public class ByteUtils {
    public static int bytes2Int(byte[] classBytes, int start, int len) {
        int sum = 0;
        int end = start + len;
        for (int i = 0; i < end; i++)
        {
            
            int n = ((int) (classBytes[i]) & 0xff);
            n <<= (--len) * 8;
            sum += (n + sum);
        }
        return sum;
    }

    public static String bytes2String(byte[] classBytes, int offset, int len) {
        return new String(classBytes, offset, len);
    }

    public static byte[] string2Bytes(String newStr) {
        return newStr.getBytes();
    }

    public static byte[] int2Byte(int value, int length) {
        byte[] b = new byte[length];
        for (int i =0; i < length; i++)
        {
            b[length -i -1 ] = (byte) ((value >> 8 * i) & 0xff);
        }
        return b;
    }

    public static byte[] bytesReplace(byte[] originalBytes, int offset, int len, byte[] replaceBytes) {
        byte[] newBytes = new byte[originalBytes.length + (replaceBytes.length - len)];
        System.arraycopy(originalBytes, 0, newBytes, 0, offset);
        System.arraycopy(replaceBytes, 0, newBytes, offset, replaceBytes.length);
        System.arraycopy(originalBytes, offset+len, newBytes, offset + replaceBytes.length, originalBytes.length - offset -len);
        return newBytes;
    }

    public static void main(String[] args) {
        byte[] array = "hello world".getBytes();
        System.out.println(bytes2Int(array,0, array.length));
    }

}
