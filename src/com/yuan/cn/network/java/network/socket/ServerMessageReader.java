package com.yuan.cn.network.java.network.socket;

import java.io.*;

public class ServerMessageReader implements MessageReader{
    @Override
    public Object readHeartBeat(InputStream inputStream) throws IOException, ClassNotFoundException {

        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        return objectInputStream.readObject();
    }

    @Override
    public String readTextMessage(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String s;
        while ((s = reader.readLine()) != null)
        {
            if (s.equals("end"))
                break;
            builder.append(s);
        }
        return builder.toString();
    }
}
