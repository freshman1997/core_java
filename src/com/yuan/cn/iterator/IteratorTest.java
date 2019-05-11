package com.yuan.cn.iterator;

import java.util.Arrays;
import java.util.Iterator;

public class IteratorTest<T> implements Iterable<T>,Cloneable{
    private Object[] obj = new Object[1];

    private int size;
    private int current = 0;

    public void add(T source)
    {
        if(size == obj.length)
        {
            // 扩张数组到一个新的长度 1.5 倍
            obj = Arrays.copyOf(obj, obj.length + obj.length << 1);
        }
        obj[size++] = source;
    }

    public boolean delete(Object o)
    {
        for (Object s : obj)
        {

        }
        return false;
    }
    public boolean delete(int index)
    {
        if(index < obj.length)
        {
            Object[] newArray = new Object[obj.length - 1];
            int i = 0;
            obj[index] = null;
            for (Object s : obj)
            {
                if(s != null)
                {
                    newArray[i++] = s;
                }
            }
            size --;
            this.obj = newArray;
            return true;
        }
        return  false;
    }

    public T get(int index)
    {
        if(index < obj.length)
        {
            return (T)obj[index];
        }
        return null;
    }
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }
    private int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (obj[i]==null)
                    return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(obj[i]))
                    return i;
        }
        return -1;
    }
    @Override
    public Iterator<T> iterator() {
        class Iter implements Iterator<T>
        {

            @Override
            public boolean hasNext() {
                if(current < size)
                {
                    return  true;
                }
                return false;
            }

            @Override
            public T next() {
                return (T)obj[current++];
            }
        }
        return new Iter();
    }


}
