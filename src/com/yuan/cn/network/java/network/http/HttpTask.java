package com.yuan.cn.network.java.network.http;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class HttpTask implements Runnable{
    private Socket socket;

    public HttpTask(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {

        if(socket == null)
            throw new IllegalArgumentException("socket can not ba null.");
        try {
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);

            Request httpRequest = HttpUtils.parseToRequest(socket.getInputStream());
            String result = null;
            if(httpRequest != null) {
                Environment environment = new Environment();
                Map<String, Object> stringObjectMap = environment.loadComponent();
                if(stringObjectMap != null)
                {
                    environment.putAutowaried(stringObjectMap);
                    for (Map.Entry<String, Object> entry : stringObjectMap.entrySet()) {
                        String s = environment.loadRequestMapping(entry.getValue(), httpRequest);
                        if (s != null)
                            result = s;
                        else {
                            result = HttpUtils.buildResponse(httpRequest, "404 not found", "text/html;charset=utf-8", 404, "ok");
                        }
                    }
                }else {
                    Request request = new Request();
                    request.setVersion("HTTP/1.1");
                    result = HttpUtils.buildResponse(request, "404 not found", "text/html;charset=utf-8", 404, "ok");
                }

            }else {
                Request request = new Request();
                request.setVersion("HTTP/1.1");
                result = HttpUtils.buildResponse(request, "404 not found", "text/html;charset=utf-8", 404, "ok");
            }


            System.out.println(result);
            try {

                writer.println(result);

            }catch (Exception e)
            {
                e.printStackTrace();
                writer.println(e.getMessage());
            }
            writer.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
