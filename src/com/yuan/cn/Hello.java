package com.yuan.cn;

import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Crazy
 * @date 2018/12/1
 */
public class Hello{
    private String filename;
    private Player player;

    public Hello(String filename) {
        this.filename = filename;
    }

    public void play() {
        try {
            BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(filename));
            player = new Player(buffer);
            player.play(2);
            player.play();
        } catch (Exception e) {
            System.out.println(e);

        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Hello mp3 = new Hello("D:\\python\\qq_music\\dist\\友谊万岁__杨千嬅香港儿童合唱团.mp3");
        executorService.execute(()-> mp3.play());

        Hello mp1 = new Hello("D:\\python\\qq_music\\醉千年__李袁杰李俊佑.mp3");
        executorService.execute(()-> mp1.play());

        Hello mp2 = new Hello("D:\\python\\qq_music\\一晃就老了__秋裤大叔.mp3");
        executorService.execute(()-> mp2.play());

    }

}
