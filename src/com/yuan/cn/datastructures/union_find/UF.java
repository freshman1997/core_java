package com.yuan.cn.datastructures.union_find;

public interface UF {
    boolean isConnected(int p, int q);
    void unionElements(int p, int q);
    int getSize();
}
