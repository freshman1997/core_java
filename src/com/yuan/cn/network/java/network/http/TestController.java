package com.yuan.cn.network.java.network.http;

import com.yuan.cn.proxy.interfaces.ProxyUtil;
import com.yuan.cn.proxy.interfaces.UserRepository;

@Controller
public class TestController {


    @RequestMapping(uri = "/tt2/{id}", production = "text/plain;charset=utf-8", type = MethodType.GET)
    public String hello(Request request, @PathVariable(name = "id") String id){
//        UserRepository repository = ProxyUtil.getRepository(UserRepository.class);
//        System.out.println(repository + "-------------");
        return ss(Integer.parseInt(id));
    }

//    @RequestMapping(uri = "/tt1/{id}", production = "text/plain;charset=utf-8", type = MethodType.POST)
//    public String hello1(Request request, @PathVariable(name = "id") String id){
//        return id + "ssssssssssssssssss";
//    }

    public static String ss(int id){
        UserRepository repository = ProxyUtil.getRepository(UserRepository.class);
        return repository.selectById(id).toString();
    }

}
