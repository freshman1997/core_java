package com.yuan.cn.bluetooth;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import java.io.IOException;

public class BluetoothTest {

    public static void main(String[] args) throws IOException {
        LocalDevice device =  LocalDevice.getLocalDevice();
        RemoteDevice[] remoteDevices = device.getDiscoveryAgent().retrieveDevices(DiscoveryAgent.PREKNOWN);

        for (RemoteDevice remoteDevice : remoteDevices) {
            System.out.println("蓝牙名称："+remoteDevice.getFriendlyName(false));
            System.out.println("蓝牙地址："+remoteDevice.getBluetoothAddress());
        }

    }
}
