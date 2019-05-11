package com.yuan.cn.datastructures.set;

import com.yuan.cn.datastructures.tree.BinarySearchTree;

public class BianrySearchTreeSet<E extends Comparable<E>> implements Set<E> {

    private BinarySearchTree<E> binarySearchTree;
    public BianrySearchTreeSet() {
        this.binarySearchTree = new BinarySearchTree<>();
    }

    @Override
    public void add(E e) {
        binarySearchTree.add(e);
    }

    @Override
    public void remove(E e) {
        binarySearchTree.remove(e);
    }

    @Override
    public boolean contains(E e) {
        return binarySearchTree.contains(e);
    }

    @Override
    public int getSize() {
        return binarySearchTree.getSize();
    }

    @Override
    public boolean isEmpty() {
        return binarySearchTree.isEmpty();
    }
}
