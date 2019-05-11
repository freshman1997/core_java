package com.yuan.cn.datastructures.map;

public class LinkedListMap<K, V> implements Map<K, V>{

    private class Node{
        K key;
        V value;
        Node next;
        Node(K key, V value, Node next){
            this.key = key;
            this.value = value;
            this.next = next;
        }
        Node(K key){this(key, null, null);}
        Node(){this(null, null, null);}

        @Override
        public String toString() {
            return key.toString()+" : "+value.toString();
        }
    }

    private Node dummyNode;
    private int size;

    public LinkedListMap() {
        dummyNode = new Node();
    }

    private Node getNode(K key){
        Node current = dummyNode.next;
        while (current != null){

            if(current.key.equals(key))
                return current;
            current = current.next;
        }
        return null;
    }
    @Override
    public void add(K key, V value) {
        Node node = getNode(key);

        if(node == null) {
            dummyNode.next = new Node(key, value, dummyNode.next);
            size++;
        }else
            node.value = value;
    }

    @Override
    public V remove(K key) {
        if(size == 0) {
            throw new IllegalArgumentException("Removed failed, list is empty.");
        }

        if(!contains(key)){
            throw new IllegalArgumentException("Removed failed, element does not exits.");
        }

        Node current = dummyNode.next;

        while (current != null){

            if(current.next!= null && current.next.key.equals(key)){
                current.next = current.next.next;
                size--;
                return current.next.value;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public boolean contains(K key) {
        return getNode(key) != null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        return node == null? null: node.value;
    }

    @Override
    public void set(K key, V value) {
        Node node = getNode(key);
        if(node == null)
            throw new IllegalArgumentException(key+" doesn't exits!");

        node.value = value;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        Node current = dummyNode.next;
        res.append("[ ");
        while (current != null){
            res.append(current.key+"=>"+current.value+", ");
            current = current.next;
        }
        String s = res.toString();
        s = s.substring(0, s.lastIndexOf(","));
        return s+" ]";
    }


}
