package com.yuan.cn.datastructures.set;

import com.yuan.cn.datastructures.link.LinkedList;

public class LinkedListSet<E extends Comparable<E>> implements Set<E>{

    private LinkedList<E> linkedList;

    public LinkedListSet() {
        linkedList = new LinkedList<>();
    }

    @Override
    public void add(E e) {
        if(!linkedList.contains(e))
            linkedList.addFirst(e);
    }

    @Override
    public void remove(E e) {
        linkedList.remove(e);
    }

    @Override
    public boolean contains(E e) {
        return linkedList.contains(e);
    }

    @Override
    public int getSize() {
        return linkedList.getSize();
    }

    @Override
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    @Override
    public String toString() {
        return linkedList.toString();
    }
}
