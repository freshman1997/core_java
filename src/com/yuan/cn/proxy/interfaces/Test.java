package com.yuan.cn.proxy.interfaces;

public class Test {
    public static void main(String[] args) {
        UserRepository repository = ProxyUtil.getRepository(UserRepository.class);
        System.out.println(repository.findAll());
    }

}
