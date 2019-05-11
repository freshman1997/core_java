package com.yuan.cn.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;

/**
 * Optional<T> 对象是一种包装对象，要么包装了类型T的对象，要么没有包装任何类型的对象。对于第一种情况，我们称这种值存在的。
 * Optional<T>类型被当作一种更安全的方式，用来替代类型T的引用，这种引用要么引用某个对象，要么位null
 * @author Crazy
 * @date 2018/12/6
 */
public class TestOptional {
    public static void main(String[] args) throws IOException {
        // 有效使用Optional的关键是要使用这样的方法：它在值不存在的情况下会产生一个可以替代物，而只有在值存在的情况下才会使用这个值
        String contents = new String(Files.readAllBytes(Paths.get("")),StandardCharsets.UTF_8);
        List<String> wordList = Arrays.asList(contents.split("\\PL+"));
        Optional<String> optionalValue = wordList.stream().filter(s -> s.contains("fred")).findFirst();
        System.out.println(optionalValue.orElse("No word")+" contains fred");
        Optional<String> empty = Optional.empty();
        String result = empty.orElse("N/A");
        System.out.println("result: "+result);

        try {

            empty.orElseThrow(IllegalArgumentException::new);
        }catch (Throwable t)
        {
            t.printStackTrace();
        }
        optionalValue = wordList.stream().filter(s->s.contains("red")).findFirst();
        optionalValue.ifPresent(s-> System.out.println(s+" contains red"));
        Set<String> results = new HashSet<>();
        optionalValue.ifPresent(results::add);
        Optional<Boolean> added = optionalValue.map(new Function<String, Boolean>() {
            @Override
            public Boolean apply(String s) {
                if(s != null)
                {
                    results.add(s);
                    return true;
                }
                return false;
            }
        });
        System.out.println(added);


    }
}
