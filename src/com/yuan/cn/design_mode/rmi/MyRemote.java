package com.yuan.cn.design_mode.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Crazy
 * @date 2018/12/7
 */
public interface MyRemote extends Remote {
    public String sayHello() throws RemoteException;
}
