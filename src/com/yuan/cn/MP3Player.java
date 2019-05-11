package com.yuan.cn;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;

public class MP3Player extends Application {
    private Sound sound;// 注意，我这里是为了防止被JVM的垃圾回收给回收掉

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 直接复制下来没效果可能是因为连接地址失效了
        sound = new Sound(new File("D:\\python\\qq_music\\dist\\友谊万岁__杨千嬅香港儿童合唱团.mp3"), false);

        sound.play();
        sound.seek();
    }

    public static void main(String[] args) {
        launch(args);// 初始化
    }


}

/**
 * 目前支持的编码格式：
 *
 * 音频：
 *
 * MP3；
 *
 * 包含非压缩PCM的AIFF；
 *
 * 包含非压缩PCM的WAV；
 *
 * 使用AAC音频的MPEG-4;（MP4）
 *
 * 使用JavaFX播放MP3文件,需要导入javafx.scene.media包
 */
class Sound extends Object {

    private Media media;// 相当于媒体播放器
    private MediaPlayer mediaPlayer;// 相当于媒体控制器

    /**
     * 播放音乐
     */
    public void play() {
        mediaPlayer.play(); // JavaFX提供的MediaPlayer有播放方法，直接调用即可
    }

    /**
     * 暂停播放
     */
    public void pause() {
        mediaPlayer.pause();// JavaFX提供的MediaPlayer有暂停方法，直接调用即可
    }

    /**
     * 停止播放
     */
    public void stop() {
        mediaPlayer.stop();// JavaFX提供的MediaPlayer有停止播放方法，直接调用即可
    }

    /**
     * 循环播放
     */
    public void loop() {
        setPlayCount(MediaPlayer.INDEFINITE);// this的方法
        play();// this的方法
    }

    /**
     * 获取现在音乐播放到哪里了
     *
     * @return 事件(单位秒)
     */
    public double getNewTime() {
        return mediaPlayer.getCurrentTime().toSeconds();
    }

    /**
     * 设置音乐的声音大小0-1
     *
     * @param v
     *            音量
     */
    public void setVolume(double v) {
        mediaPlayer.setVolume(v);
    }

    /**
     *
     * @param count
     *            设置媒体文件循环播放的次数
     */
    public void setPlayCount(int count) {
        mediaPlayer.setCycleCount(count);
    }

    /**
     * 释放媒体文件占用的空间
     */
    public void close() {
        mediaPlayer.dispose(); // 不是用Media类来释放内存，要用MediaPlayer控制类来释放内存
        System.gc(); // 通知JVM内存回收,调用了dispose方法内存并不会马上被回收，会被标记成垃圾，等待下一次垃圾回收的执行,这里我们手动调用
    }
    public void setRate(double rate){
        mediaPlayer.setRate(rate);
    }
    public void seek()
    {
        mediaPlayer.seek(new Duration(10*1000));
    }
    /**
     *
     * @param URL
     *            媒体文件目录，本地文件也要用URL(String)路径
     */
    public Sound(String URL, boolean isAutoPlay) {
        this.media = new Media(URL);
        this.mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setAutoPlay(isAutoPlay);

    }

    /**
     *
     * @param URL
     *            虽然直接传入的事URL，但是是不能直接使用，要转成String类型的URL
     */
    public Sound(URL URL, boolean isAutoPlay) {
        this(URL.toString(), isAutoPlay);
    }

    /**
     *
     * @param file
     *            最终转换成URL(String)路径
     */
    public Sound(File file, boolean isAutoPlay) {
        this(file.toURI().toString(), isAutoPlay);
    }

}
