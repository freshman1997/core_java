package com.yuan.cn.network.bio_room.lib.utils;

import java.io.Closeable;
import java.io.IOException;

public class CloseUtils {

    public static void close(Closeable... closeables)
    {
        if (closeables == null)
        {
            return;
        }

        for (Closeable closeable : closeables) {
            // 执行关闭操作
            try {
                closeable.close();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }
}
