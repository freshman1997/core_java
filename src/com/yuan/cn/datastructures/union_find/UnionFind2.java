package com.yuan.cn.datastructures.union_find;

public class UnionFind2 implements UF{
    private int parent[];
    public UnionFind2(int size){
        parent = new int[size];
        // 初始化的时候并没有连接操作，所以初始化为i
        for(int i = 0; i< size;i++)
            parent[i] = i;
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public void unionElements(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if(qRoot == pRoot)
            return;
        // 让p所在的根节点连接到q所在的根节点上
        // O(h) 复杂度，h为树的高度
        parent[pRoot] = qRoot;
    }
    private int find(int p){
        if(p<0||p >= parent.length)
            throw new IllegalArgumentException("Index illegal.");
        //p == parent[p]认为已经到达根节点了
        while (p != parent[p]){
            p = parent[p];
        }
        return p;
    }
    @Override
    public int getSize() {
        return parent.length;
    }
}
