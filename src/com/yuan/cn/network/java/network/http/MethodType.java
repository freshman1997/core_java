package com.yuan.cn.network.java.network.http;

public enum MethodType {
    GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE");

    private String method;
    MethodType(String method)
    {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
