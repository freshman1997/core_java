package ffmpeg;

import java.util.ArrayList;
import java.util.List;

public class FFMPEGtestUsingJavaExecute {

    public static boolean executeCodecs(String ffmpegPath, String upFilePath, String codcFilePath, String mediaPicPath) throws Exception
    {
        // 创建一个List来保存转换视频文件为flv格式命令
        List<String> list = new ArrayList<>();
        list.add(ffmpegPath);
        list.add("-i"); // 指定要转换的文件
        list.add(upFilePath);
        list.add("-qscale"); // 指定转换的质量
        list.add("6");
        list.add("-ab"); // 设置音频码率
        list.add("64");
        list.add("-ac"); // 设置声道数
        list.add("2");
        list.add("-ar"); // 设置声音的采样率
        list.add("22050");
        list.add("-r"); // 设置帧率
        list.add("24");
        list.add("-y"); // 指定将覆盖已存在的文件
        list.add(codcFilePath);

        List<String> cutpic = new ArrayList<>();
        cutpic.add(ffmpegPath);
        cutpic.add("-i");
        cutpic.add(upFilePath);
        cutpic.add("-y");
        cutpic.add("-f");
        cutpic.add("image2");
        // 指定截取的起始时间
        cutpic.add("-ss");
        // 起始时间为第2秒
        cutpic.add("2");
        // 该参数指定持续时间
        cutpic.add("-t");
        // 添加持续时间为1毫秒
        cutpic.add("0.001");
        // 添加参数 " -s ",该参数指定截取的图片大小
        cutpic.add("-s");
        // 截取的图片大小为 350 * 240
        cutpic.add("800*280");

        // 添加截取的图片的保存路径
        cutpic.add(mediaPicPath);

        boolean mark = true;
        ProcessBuilder builder = new ProcessBuilder();
        try {
            builder.command(list);
            // 如果此属性为true，则任何由通过此对象的start()方法启动的后续子进程生成的错误输出都将与标准输出合并
            // 因此两者均可使用Process.getInputStream()方法读取。这使得关联错误消息和相应的输出变得更容易
            builder.redirectErrorStream(true);
            builder.start();
            builder.command(cutpic);
            builder.redirectErrorStream(true);
            builder.start();
        }catch (Exception e)
        {
            mark =false;
            System.out.println(e);
            e.printStackTrace();
        }
        return mark;
    }

    public static void main(String[] args) throws Exception {
        executeCodecs("E:\\ffmpeg\\soft\\ffmpeg-20181128-b9aff7a-win64-static\\bin\\ffmpeg.exe", "E:\\英雄时刻\\1905269298\\英雄时刻_20181010-20点45分58s.avi", "E:\\英雄时刻\\a.flv", "E:\\英雄时刻b.jpg");
    }
}
