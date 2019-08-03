package com.yuan.cn.datastructures.link;

public class LinkedList<E> {

    private Node dummyHead;
    private int size;
    public LinkedList() {
        dummyHead = new Node(null, null);
        size = 0;
    }

    private class Node{
        public E e;
        public Node next;

        Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }
        Node(E e) {
            this(e, null);
        }
        Node()
        {
            this(null, null);
        }
    }
    public int getSize(){return size;}
    public boolean isEmpty(){return size == 0;}

    public void add(int index, E e){
        if(index < 0 || index > size)
            throw new IllegalArgumentException("Add failed, illegal index.");

        // 移动查找节点，直到找到插入的位置的前一个节点 也就是 prev
        Node prev = dummyHead;
        for(int i =0; i< index; i++)
            prev = prev.next;

//        Node node = new Node(e);
//        node.next = prev.next;
//        prev.next = node;
        prev.next = new Node(e, prev.next);
        size++;
    }
    public void addLast(E e)
    {
        add(size, e);
    }
    public void addFirst(E e){
        add(0, e);
    }

    public E get(int index){
        if(index < 0|| index >= size)
            throw new IllegalArgumentException("Get failed, illegal index.");

        Node current = dummyHead.next;
        for(int i = 0;i<index; i++)
            current = current.next;
        return current.e;
    }
    public E getFirst()
    {
        return get(0);
    }public E getLast()
    {
        return get(size-1);
    }
    public void set(int index, E e){
        if(index < 0 || index > size)
            throw new IllegalArgumentException("Set failed, illegal index.");
        //从第一个真正的元素开始
        Node currentNode = dummyHead.next;
        for(int i = 0;i<index; i++)
            currentNode = currentNode.next;

        currentNode.e = e;
    }

    public boolean contains(E e){
        Node current = dummyHead.next;
        while (current != null)
        {
            if(current.e == e)
            {
                return true;
            }
            current = current.next;
        }
        return false;
    }
    public void remove(E e){
        if(size == 0)
            throw new IllegalArgumentException("Removed failed, list is empty.");
        if(!contains(e)){
            throw new IllegalArgumentException("Removed failed, element does not exits.");
        }
        Node prev = dummyHead.next;

        while (prev != null){

            if(prev.next!= null && prev.next.e.equals(e)){
                prev.next = prev.next.next;
                size--;
                return;
            }
            prev = prev.next;
        }


    }
    public E remove(int index)
    {
        if(index < 0|| index >= size)
            throw new IllegalArgumentException("Remove failed, illegal index.");
        Node prev = dummyHead;
        // 获取到要删除的前一个元素
        for(int i = 0;i<index; i++)
            prev = prev.next;

        Node delNode = prev.next;
        prev.next = delNode.next;
        delNode.next = null;
        size--;
        return delNode.e;
    }
    public E removeFirst()
    {
        return remove(0);
    }public E removeLast()
    {
        return remove(size-1);
    }
    @Override
    public String toString() {
        StringBuilder res= new StringBuilder();
//        Node current = dummyHead.next;
//        while (current != null)
//        {
//            res.append(current+"->");
//            current = current.next;
//        }
        for(Node cur = dummyHead.next; cur != null; cur = cur.next)
            res.append(cur.e+"->");
        res.append("NULL");
        return res.toString();
    }



}
