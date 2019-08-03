package com.yuan.cn.datastructures.link;

/**
 * 循环链表
 */
public class LoopLinkedList<T> {
    class Node{
        T data;
        Node  next;
        Node(T data){
            this.data = data;
        }
    }
    private Node dummyHead;
    private int size = 0;

    public LoopLinkedList(){
        dummyHead = new Node(null);
    }
    public void add(int index, T element){

        Node prev = dummyHead;

        for (int i = 0; i < index; i ++){
            prev = prev.next;
        }
        Node current = new Node(element);
        current.next = prev.next;
        prev.next = current;

        size++;
    }

    public void addFirst(T element){
        add(0, element);
    }
    public void addLast(T element){
        add(size, element);
    }
    public T get(int index){
        Node prev = dummyHead;
        for (int i = 0; i < index; i ++){
            prev = prev.next;
        }
        return prev.next.data;
    }

    public static void main(String[] args) {
        LoopLinkedList<Integer> loopLinkedList = new LoopLinkedList<>();
        loopLinkedList.addFirst(1);
        loopLinkedList.addFirst(2);
        loopLinkedList.addFirst(3);
        loopLinkedList.addFirst(4);
        System.out.println(loopLinkedList.get(2));
    }
}
