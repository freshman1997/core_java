package com.yuan.cn.datastructures.stack;

import com.yuan.cn.datastructures.array.Array;

public class ArrayStack<E> implements Stack<E> {
    Array<E> array;
    ArrayStack(int capacity){array = new Array<>(capacity);}
    ArrayStack(){array = new Array<>();}
    @Override
    public void push(E e) {
        array.addLast(e);
    }
    @Override
    public E pop() {
        return array.removeLast();
    }

    @Override
    public E peek() {
        return array.getLast();
    }

    @Override
    public int getSize() {
        return array.getSize();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }
    public int getCapacity()
    {
        return array.getCapacity();
    }

    @Override
    public String toString() {
        StringBuilder resource = new StringBuilder();
        resource.append("Stack [");
        for(int i =0;i< array.getSize(); i++){
            resource.append(array.get(i));
            if( i != array.getSize() -1)
                resource.append(", ");
        }
        resource.append("] top");
        return resource.toString();
    }
}
