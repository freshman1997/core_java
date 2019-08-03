package com.yuan.cn.function;

import java.util.Iterator;

public class ResultImpl<T> implements Result<T>{
    private T[] data = (T[]) new Object[10];

    @Override
    public T getResult() {
        return null;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int i = 0;
            @Override
            public boolean hasNext() {
                return i >= 10;
            }

            @Override
            public T next() {
                return data[i++];
            }
        };
    }

}

