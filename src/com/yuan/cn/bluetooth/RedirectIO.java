package com.yuan.cn.bluetooth;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * 重定向标准IO
 */
public class RedirectIO {
    public static void main(String[] args) throws IOException {
        PrintStream out = System.out;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("pride-and-prejudice.txt"));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("test.txt"));
        PrintStream printStream = new PrintStream(bufferedOutputStream);

        System.setOut(printStream);
        System.setIn(bufferedInputStream);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String s;
        while ((s = reader.readLine()) != null)
            System.out.println(s);

        printStream.close();
        System.setOut(out);
        FileChannel channel = new FileInputStream("pride-and-prejudice.txt").getChannel();
        FileChannel channel1 = new FileOutputStream("D:/pride-and-prejudice.txt").getChannel();
        channel.transferTo(0, channel.size(), channel1);
        channel1.transferFrom(channel, 0, channel.size());
    }
}
