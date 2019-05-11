package com.yuan.cn.function;

import java.util.function.Function;

@FunctionalInterface
public interface TestFunctionInterface {
    <T> void showResult(Function<? extends T,? extends Object> function);
}
