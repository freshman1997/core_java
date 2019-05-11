package com.yuan.cn.network.demo6.clink.core;

import java.io.Closeable;
import java.io.IOException;

public interface Sender extends Closeable {
    boolean sendAsync(IoArgs args, IoArgs.IoArgsEventListener listener) throws IOException;
}