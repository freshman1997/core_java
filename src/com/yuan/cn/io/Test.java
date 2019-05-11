package com.yuan.cn.io;

import java.math.BigInteger;
import java.nio.file.Files;
import java.util.stream.Stream;

/**
 * @author Crazy
 * @date 2018/12/3
 */
public class Test {
    public static void main(String[] args) {
        t1();
    }
    public static void t1()
    {
        Stream<String> gentle = Stream.of("gentle", "down", "the", "stream");
        gentle.forEach(System.out::println);
        Stream<String> empty = Stream.empty();
        // 创建无限流的方法 该方法接收一个不包含任何引元的函数（是一个Supplier<T>接口的对象）
        Stream<String> generate = Stream.generate(() -> "hello");
        Stream<Double> generate1 = Stream.generate(Math::random);
        // 产生无限序列 如 0，1，2，3 会反复地将该函数应用到之前的结果上
        Stream.iterate(BigInteger.ZERO,n -> n.add(BigInteger.ONE));
        Stream.builder().accept("hello");
    }
}
