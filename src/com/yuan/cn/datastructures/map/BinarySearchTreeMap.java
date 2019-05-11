package com.yuan.cn.datastructures.map;

public class BinarySearchTreeMap<K extends Comparable, V> implements Map<K, V> {

    private class Node{
        K key;
        V value;
        Node left;
        Node right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = right = null;
        }
    }
    private Node root;
    private int size;

    @Override
    public void add(K key, V value) {
        root = add(root, key, value);
    }

    private Node add(Node node, K key, V value) {
        if(node == null){
            size++;
            return new Node(key, value);
        }

        if(node.key.compareTo(key) < 0){
            node.left = add(node.left, key, value);
        }else if(node.key.compareTo(key) > 0){
            node.right = add(node.right, key, value);
        }else {
            node.value = value;
        }
        return node;
    }
    private Node getNode(Node node, K key){
        if(node == null)
            return null;

        if(node.key.compareTo(key) == 0){
            return node;
        }else if(node.key.compareTo(key) < 0){
            return getNode(node.left, key);
        }else {
            return getNode(node.right, key);
        }

    }
    @Override
    public V remove(K key) {
        Node node = getNode(root, key);
        if(node != null)
        {
            root = remove(root, key);
            return node.value;
        }
        return null;
    }

    private Node remove(Node node, K key) {
        if(node == null)
            return null;

        if(key.compareTo(node.key) < 0){
            node.left = remove(node.left, key);
            return node;
        }else if(key.compareTo(node.key) > 0){
            node.right = remove(node.right, key);
            return node;
        } else{
            if(node.left == null){
                Node rightNode = node.right;
                node.right = null;
                size --;
                return rightNode;
            }

            if(node.right == null){
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }
            // 左右都有的节点要删除，找到比待删除节点打的最小节点，即待删除节点右子树的最小节点
            // 用这个节点代替待删除的节点的位置
            // 这是后继写法   还有一个前驱写法，也就是左子树的最近最大的节点来替代要删除的节点
            Node successor = getMin(node.right);
            successor.right = removeMin(node.right);

            successor.left = node.left;
            node.right = node.left = null;

            return successor;
        }
    }
    private Node removeMin(Node node) {
        if(node.left == null)
        {
            Node rightNode = node.right;
            node.right = null;
            size --;
            return rightNode;
        }
        // 返回的右子节点作为头结点
        node.left = removeMin(node.left);
        // 已经删除完成之后的新的树的根节点 返回
        return node;
    }
    private Node getMin(Node node){
        if(node.left == null)
            return node;
        return getMin(node.left);
    }

    @Override
    public boolean contains(K key) {
        return getNode(root, key) != null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(root, key);
        return node == null? null : node.value;
    }

    @Override
    public void set(K key, V value) {
        Node node = getNode(root, key);
        if(node == null)
            throw new IllegalArgumentException(key+" does not exits!");
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    public static void main(String[] args) {
        BinarySearchTreeMap<Integer, Integer> map = new BinarySearchTreeMap<>();
        for(int i = 0;i< 10;i++){
            map.add(i, i*10);
        }
        for(int i = 0;i< 10;i++){
            System.out.println(map.get(i));
        }
        System.out.println("================");
        System.out.println(map.remove(1));


    }
}
