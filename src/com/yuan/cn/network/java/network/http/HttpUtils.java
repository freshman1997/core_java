package com.yuan.cn.network.java.network.http;

import com.sun.deploy.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public final class HttpUtils {

    private static void decodeRequestLine(BufferedReader reader, Request request) throws IOException {

        String s = reader.readLine();
        System.out.println(s);
        String[] strs = StringUtils.splitString(s, " ");
        assert strs.length == 3;
        request.setMethod(strs[0]);
        request.setUri(strs[1]);
        request.setVersion(strs[2]);

    }
    private static void decodeRequestHeader(BufferedReader reader, Request request) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String line = reader.readLine();
        String[] kv;

        while (!"".equals(line)){
            kv = StringUtils.splitString(line, ":");

            assert kv.length == 2;
            headers.put(kv[0].trim(), kv[1].trim());
            line = reader.readLine();
        }
        request.setHeaders(headers);
    }

    private static void decodeRequestMessage(BufferedReader reader, Request request) throws IOException {
        int contentLength = Integer.parseInt(request.getHeaders().getOrDefault("Content-Length", "0"));

        if(contentLength == 0){
            // 表示没有message，直接返回
            // 如get/option请求就没有message
            return;
        }
        char[] message = new char[contentLength];

        reader.read(message);

        request.setMessage(new String(message));
    }

    public static Request parseToRequest(InputStream reqInputStream){

        BufferedReader httpReader = new BufferedReader(new InputStreamReader(reqInputStream));

        Request httpRequest = new Request();

        try {

            decodeRequestLine(httpReader, httpRequest);
            decodeRequestHeader(httpReader, httpRequest);
            decodeRequestMessage(httpReader, httpRequest);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return httpRequest;
    }


    public static String buildResponse(Request httpRequest, String result, String type, int code, String status) {
        Response httpResponse = new Response();
        httpResponse.setCode(code);
        httpResponse.setStatus(status);
        httpResponse.setVersion(httpRequest.getVersion());

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", type);
        headers.put("Content-Length", String.valueOf(result.getBytes().length));

        httpResponse.setHeaders(headers);

        httpResponse.setMessage(result);

        StringBuilder builder = new StringBuilder();
        builderResponseLine(httpResponse, builder);
        builderResponseHeaders(httpResponse, builder);
        builderResponseMessage(httpResponse, builder);
        return builder.toString();
    }

    private static void builderResponseMessage(Response response, StringBuilder builder) {
        builder.append(response.getMessage());
    }

    private static void builderResponseHeaders(Response response, StringBuilder builder) {
        for (Map.Entry<String, String> entry : response.getHeaders().entrySet()) {
            builder.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }
        builder.append("\n");
    }

    // 请求行  请求类型 路径 http版本
    private static void builderResponseLine(Response response, StringBuilder builder) {
        builder.append(response.getVersion()).append(" ")
                .append(response.getCode()).append(" ")
                .append(response.getStatus()).append("\n");
    }
}
