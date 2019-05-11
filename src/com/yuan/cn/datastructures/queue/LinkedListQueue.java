package com.yuan.cn.datastructures.queue;

import org.junit.Test;

public class LinkedListQueue<E> implements Queue<E>{
    private Node head, tail;
    private int size;

    public LinkedListQueue() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void enqueue(E e) {
        if(tail == null){
            tail = new Node(e);
            head = tail;
        }else {
            tail.next = new Node(e);
            tail = tail.next;
        }
        size ++;
    }

    @Override
    public E dequeue() {
        if(isEmpty())
            throw new IllegalArgumentException("Can not dequeue from an empty queue.");

        Node retNode = head;
        head = head.next;
        retNode.next = null;
        // 如果只有一个元素的话
        if(head == null)
            tail = null;
        size--;
        return retNode.e;
    }

    @Override
    public E getFront() {
        if(isEmpty())
            throw new IllegalArgumentException("Queue is empty.");
        return null;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for(Node cur = head; cur != null; cur = cur.next)
            res.append(cur.e+"->");
        res.append("NULL");
        return res.toString();
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    private class Node{
        public E e;
        public Node next;

        Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }
        Node(E e){this(e, null);}
        Node(){this(null, null);}

        @Override
        public String toString() {
            return e.toString();
        }
    }

    @Test
    public void test()
    {
        LinkedListQueue<Integer> linkedListQueue = new LinkedListQueue<>();
        for(int i =0; i<10;i++){
            linkedListQueue.enqueue(i);
        }
        System.out.println(linkedListQueue);
    }
}
