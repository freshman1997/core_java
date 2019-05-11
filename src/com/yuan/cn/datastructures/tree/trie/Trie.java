package com.yuan.cn.datastructures.tree.trie;
import java.util.TreeMap;

public class Trie {
    private class Node{
        boolean isWord;
        TreeMap<Character, Node> next;
        Node(boolean isWord){
            this.isWord = isWord;
        }
        Node(){this(false);}
    }
    private Node root;
    private int size;
    Trie(){
        root = new Node();
        size = 0;
    }
    public int getSize(){return size;}
    public boolean isEmpty(){return size == 0;}

    public void add(String word){
//        Node cur = root;
//        for(int i =0 ;i< word.length(); i++){
//            char c = word.charAt(i);
//            // 如果当前的节点已经包含了这个char
//            if(cur.next.get(c) == null)
//                cur.next.put(c, new Node());
//            cur = cur.next.get(c);
//        }
//        // 在当前已经存在的单词中不存在这个单词 false
//        if(!cur.isWord){
//            cur.isWord = true;
//            size++;
//        }
        // 递归写法
        add(word, root);
    }
    private void add(String c, Node node){
        int i = 0;
        if(node.next.get(c.charAt(i)) == null)
        {
            Node cur = new Node();
            node.next.put(c.charAt(i), cur);
        }else
            node = node.next.get(c.charAt(i));
        i++;
        if(!node.isWord && i == c.length()){
            node.isWord = true;
            size++;
            return;
        }
        add(c, node);
    }
    public boolean contains(String word){
        Node cur = root;
        for(int i =0 ;i< word.length(); i++){
            char c= word.charAt(i);
            if(cur.next.get(c) == null)
                return false;
            cur = cur.next.get(c);
        }
        // 如果找到了这个单词，但是之前并没有存进去，所以判断是否是一个单词
        return cur.isWord;
    }
    public boolean isPrefix(String prefix){
        Node cur = root;
        for(int i = 0 ;i< prefix.length(); i++){
            char c= prefix.charAt(i);
            if(cur.next.get(c) == null)
                return false;
            cur = cur.next.get(c);
        }
        return true;
    }
    public boolean search(String word){
        return match(root, word, 0);
    }
    private boolean match(Node node, String word, int index){
        if(index == word.length())
            return node.isWord;
        char c = word.charAt(index);
        if(c != '.'){
            if(node.next.get(c) == null)
                return false;
            return match(node.next.get(c), word, index+1);
        }else {
            for(char nextChar : node.next.keySet())
            {
                if(match(node.next.get(nextChar), word, index+1))
                    return true;
            }
            return false;
        }
    }
    // 待实现
    public boolean remove(String word){
        Node cur = root;
        if(contains(word)){
            // 获取到最后一个char 的前一个
            for(int i =0 ;i< word.length() - 1; i++) {
                char c = word.charAt(i);
                cur = cur.next.get(c);
            }
            Node del = cur.next.get(word.charAt(word.length()-1));
            // 没有下一个了，也就是到达尾节点了
            if(del.next == null)
                cur.next = null;
            else
                cur.next = del.next;

            return true;
        }else
            return false;

    }

}
