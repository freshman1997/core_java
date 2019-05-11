package com.yuan.cn.datastructures.stack;

import com.yuan.cn.datastructures.link.LinkedList;

import java.util.Random;

public class LinkedStack<E> implements Stack<E>{
    private LinkedList<E> linkedList;
    public LinkedStack(){
        linkedList = new LinkedList<>();
    }

    @Override
    public void push(E e) {
        linkedList.addFirst(e);
    }

    @Override
    public E pop() {
        return linkedList.removeFirst();
    }

    @Override
    public E peek() {
        return linkedList.getFirst();
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
        StringBuilder res = new StringBuilder();
        res.append("stack top [");
        res.append(linkedList);
        res.append(" ]");
        return res.toString();
    }
    private static double testQueue(Stack<Integer> q, int count)
    {
        long startTime = System.nanoTime();
        Random random = new Random();
        for(int i = 0; i< count; i++)
        {
            q.push(random.nextInt(Integer.MAX_VALUE));
        }
        for(int i = 0; i< count; i++) {
            q.pop();
        }
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1000000000.0;
    }
    public static void main(String[] args) {
        // 在十万的时候是链表栈快，但是*10之后就是数组栈快，在*10之后又是链表栈快
        int opCount = 1000000;
        Stack<Integer> arrayQueue = new ArrayStack<>();
        double time1 = testQueue(arrayQueue, opCount);
        System.out.println("ArrayStack took time: "+time1);

        Stack<Integer> loopQueue = new LinkedStack<>();
        double time2 = testQueue(loopQueue, opCount);
        System.out.println("LinkedStack took time: "+time2);
    }
}
