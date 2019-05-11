package com.yuan.cn.datastructures.union_find;

public class UnionFind3 implements UF{
    private int parent[];
    private int sz[];
    public UnionFind3(int size){
        parent = new int[size];
        sz = new int[size];
        // 初始化的时候并没有连接操作，所以初始化为i
        for(int i = 0; i< size;i++)
        {
            parent[i] = i;
            sz[i] = 1;
        }
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
        // 让元素个数比较少的执行元素个数比较多的根节点
        if(sz[pRoot] < sz[qRoot])
        {
            parent[pRoot] = qRoot;
            sz[qRoot] += sz[pRoot];
        }else
        {
            parent[qRoot] = pRoot;
            sz[pRoot] += sz[qRoot];
        }
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
