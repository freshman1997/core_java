package com.yuan.cn.stream;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CountWordTest {

    public static void main(String[] args) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("D:\\logs\\mall.log")),StandardCharsets.UTF_8);
        List<String> list = Arrays.asList(content.split("\\r\\n"));
        long count = list.stream().filter(w -> w.length() > 12).count();
        System.out.println(count);
        Optional<String> optional  = Optional.of("sssss");
//        String s = optional.filter(s1 -> s1.length() > 10).orElseThrow(IllegalArgumentException::new);
//        System.out.println(s);
        List<String> list1 = new ArrayList<>();
        Stream<Integer> integerStream = list.stream().filter(item -> item.length() > 10).map(String::length);
//        integerStream.forEach(System.out::println);
        Optional<String> optionalS = optional.map(String::toUpperCase);
        System.out.println(optionalS.get());
        long start = System.currentTimeMillis();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        FileChannel channel = FileChannel.open(Paths.get("D:\\software\\pandownload\\data\\2037-荣耀战场-前进30高地\\[BTpig]荣耀战场-前进30高地v1.10繁体中文硬盘版.rar"));
        channel.read(buffer);
        int len;
        Path path = Paths.get("d:/logs/aa.rar");
        boolean exists = Files.exists(path);
        if (! exists)
            Files.createFile(path);
        FileChannel writer = FileChannel.open(Paths.get("d:/logs/aa.rar"), StandardOpenOption.WRITE);
        while ((len = channel.read(buffer)) != -1){
            System.out.println(len);
            buffer.clear();
            writer.write(buffer);

            buffer.rewind();
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时： " + (end - start) / 1000 + " 秒");
        channel.close();
        writer.close();

    }
}
class User implements Serializable {
    private int id;
    private String no;
    private String introduce;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
