package com.yuan.cn.datastructures.union_find;

public class UnionFind6 implements UF{
    private int parent[];
    private int rank[]; // rank[i]表示以i为根的集合所表示的树的层数

    public UnionFind6(int size){
        parent = new int[size];
        rank = new int[size];
        // 初始化的时候并没有连接操作，所以初始化为i
        for(int i = 0; i< size;i++)
        {
            parent[i] = i;
            rank[i] = 1;
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
        // 根据两个元素所在的树的rank不同判断合并方向
        // 将rank低的集合合并到rank高的集合上
        if(rank[pRoot] < rank[qRoot])
        {
            parent[pRoot] = qRoot;
//            sz[qRoot] += sz[pRoot];
        }else if(rank[pRoot] > rank[qRoot])
        {
            parent[qRoot] = pRoot;
//            sz[pRoot] += sz[qRoot];
        }else {
            parent[qRoot] = pRoot;
            rank[pRoot] += rank[qRoot];
        }
    }
    private int find(int p){
        if(p<0||p >= parent.length)
            throw new IllegalArgumentException("Index illegal.");

        if(p != parent[p]){
            parent[p] = find(parent[p]);
        }
        return parent[p];
    }
    @Override
    public int getSize() {
        return parent.length;
    }
}
