package com.yuan.cn.network.java.network.http;

@Controller
public class TestController {

    @RequestMapping(uri = "/tt2", production = "text/plain;charset=utf-8", type = MethodType.GET)
    public String hello(Request request){
        return "你好世界！";
    }

}
