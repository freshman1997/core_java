package com.yuan.cn;

import javax.media.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Test extends JFrame {
    public Test(){
        this.setTitle("MyCapture");
        this.setBounds(500,100,800,500);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public void start(){
        String str2 = "vfw:Microsoft WDM Image Capture (Win32):0";
        CaptureDeviceInfo di = null;
        MediaLocator ml = null;
        Player player=null;

        di=CaptureDeviceManager.getDevice(str2);
        ml = di.getLocator();
        System.out.println(di);
        System.out.println(ml);
        try {
            player=Manager.createRealizedPlayer(ml);
        } catch (NoPlayerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CannotRealizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(player!=null)
            player.start();
        Component comp = null;
        if((comp=player.getVisualComponent())!=null)
            add(comp,BorderLayout.CENTER);
    }
    public static void main(String[] args){
        Test mc = new Test();
        mc.start();
        System.out.println("sss");
    }
}
