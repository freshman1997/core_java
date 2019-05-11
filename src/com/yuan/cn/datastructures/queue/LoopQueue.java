package com.yuan.cn.datastructures.queue;

import java.util.Random;

public class LoopQueue<E> implements Queue<E>{
    private E data[];
    private int front, tail;
    private int size;
    public LoopQueue(int capacity){
        data = (E[]) new Object[capacity + 1];
        front = 0;
        tail = 0;
    }
    public LoopQueue(){
        data = (E[]) new Object[10 + 1];
        front = 0;
        tail = 0;
    }

    @Override
    public void enqueue(E e) {
        // 判断是否是满的数组
        if((tail + 1) % data.length == front)
        {
            resize(getCapacity() * 2);
        }

        data[tail] = e;
        tail = (tail + 1) % data.length;
        size ++;
    }

    private void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity*2 + 1];
        for(int i =0; i< size; i++){
            newData[i] = data[front + i];
        }
        data = newData;
        front = 0;
        tail = size;
    }

    @Override
    public E dequeue() {
        if(isEmpty()){
            throw new IllegalArgumentException("Can not dequeue from an empty queue.");
        }
        E ret = data[front];

        data[front] = null;
        front = (front+1) % data.length;
        size--;
        if(size == getCapacity() /4 && getCapacity()/2 != 0){
            // 缩容
            resize(getCapacity()/2);
        }
        return ret;
    }

    @Override
    public E getFront() {
        if(isEmpty()){
            throw new IllegalArgumentException("Queue is empty.");
        }
        return data[front];
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return front == tail;
    }
    public int getCapacity()
    {
        return data.length - 1;
    }

    @Override
    public String toString() {
        StringBuilder resource = new StringBuilder();
        resource.append(String.format("Queue: size = %d, capacity = %d", size, getCapacity()));
        resource.append("front [");
        for(int i = front; i != tail; i = (i + 1)% data.length)
        {
            resource.append(data[i]);
            if((i + 1) % data.length != tail)
                resource.append(", ");
        }
        resource.append("] tail");
        return resource.toString();
    }

    private static double testQueue(Queue<Integer> q, int count)
    {
        long startTime = System.nanoTime();
        Random random = new Random();
        for(int i = 0; i< count; i++)
        {
            q.enqueue(random.nextInt(Integer.MAX_VALUE));
        }
        for(int i = 0; i< count; i++) {
            q.dequeue();
        }
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1000000000.0;
    }
    public static void main(String[] args) {
        int opCount = 100000;
        Queue<Integer> arrayQueue = new ArrayQueue<>();
        double time1 = testQueue(arrayQueue, opCount);
        System.out.println("Array queue took time: "+time1);

        Queue<Integer> loopQueue = new LoopQueue<>();
        double time2 = testQueue(loopQueue, opCount);
        System.out.println("Loop queue took time: "+time2);

        Queue<Integer> linkedListQueue = new LinkedListQueue<>();
        double time3 = testQueue(linkedListQueue, opCount);
        System.out.println("Linked List queue took time: "+time3);
    }
}
