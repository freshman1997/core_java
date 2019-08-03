package com.yuan.cn.function;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Saying<T>{
    private T name;
    private List<T> list = new ArrayList<>();
    public void say(String name) {

    }

    public void showResult(Consumer<T> consumer) {
        for (T t : list)
        {
            consumer.accept(t);
        }
    }

    @Override
    public String toString() {
        return "Saying{" +
                "name='" + name + '\'' +
                '}';
    }

    public T getName() {
        return name;
    }

    public void setName(T name) {
        list.add(name);
        this.name = name;
    }
}
