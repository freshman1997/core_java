package com.yuan.cn.function;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    private String name;
    public static String sayHello(String name)
    {
        return name+"====";
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        Saying<String> saying = new Saying<>();
        saying.setName("hello ");
        saying.setName("world!");
        saying.showResult(System.out::print);
    }
    private static void captureScreen(String fileName, String folder) throws Exception {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);
        Robot robot = new Robot();

        BufferedImage image = robot.createScreenCapture(screenRectangle);
        // 截图保存的路径
        File screenFile = new File(fileName);
        // 如果路径不存在,则创建
        if (!screenFile.getParentFile().exists()) {
            screenFile.getParentFile().mkdirs();
        }
        //判断文件是否存在，不存在就创建文件
        if(!screenFile.exists()&& !screenFile .isDirectory()) {
            screenFile.mkdir();
        }

        File f = new File(screenFile, folder);
        ImageIO.write(image, "png", f);
        //自动打开
        /*if (Desktop.isDesktopSupported()
                 && Desktop.getDesktop().isSupported(Desktop.Action.OPEN))
                    Desktop.getDesktop().open(f);*/
    }
    @Test
    public void test()
    {
        Date dt=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMddHHmmss");
        String data=sdf.format(dt);
        String rd=sdf1.format(dt);
        try {
            captureScreen("D:\\image\\"+data,rd+".png");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
