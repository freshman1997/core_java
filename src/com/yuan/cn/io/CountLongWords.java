package com.yuan.cn.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * @author Crazy
 * @date 2018/12/3
 */
public class CountLongWords {
    public static void main(String[] args) throws IOException {
        String contents = new String(Files.readAllBytes(Paths.get("D:\\新大纲大学英语四级词汇列表.txt")),StandardCharsets.UTF_8);
        List<String> list = Arrays.asList(contents.split("\\PL+"));
        long count = 0L;
        for (String w : list)
        {
            if(w.length() > 12)
            {
                count++;
            }
        }
        System.out.println(count);

        count = list.parallelStream().filter(word -> word.length() > 12).count();
        System.out.println(count);
    }
}
