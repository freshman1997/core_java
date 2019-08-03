package com.yuan.cn.datastructures.heap;

import com.yuan.cn.datastructures.array.Array;
import org.junit.Test;

import java.util.Random;

public class MaxHeap<E extends Comparable<E>> {

    private Array<E> data;

    public MaxHeap(int capacity){
        data = new Array<>(capacity);
    }

    public MaxHeap(){data = new Array<>();}

    public MaxHeap(E[] arr){
        data = new Array<>(arr);
        for(int i = parent(arr.length - 1); i >= 0; i--){
            shiftDown(i);
        }
    }

    public int size(){return data.getSize();}

    public boolean isEmpty(){return data.isEmpty();}
    // 返回完全二叉树的数组表示中，一个索引所表示的元素的父亲节点的索引
    private int parent(int index){
        if(index == 0)
            throw new IllegalArgumentException("index 0 does not have parent.");
        return (index - 1) / 2;
    }
    // 返回完全二叉树的数组表示中，一个索引所表示的元素的左孩子节点的索引
    private int leftChild(int index){
        return index * 2 + 1;
    }
    // 返回完全二叉树的数组表示中，一个索引所表示的元素的右孩子节点的索引
    private int rightChild(int index){
        return index * 2 + 2;
    }

    // 向堆中添加元素
    public void add(E e){
        data.addLast(e);
        shiftUp(data.getSize() - 1);
    }
    // 数据上浮
    private void shiftUp(int index) {
        while ( index > 0 && data.get(parent(index)).compareTo(data.get(index)) < 0){
            data.swap(index, parent(index));
            index = parent(index);
        }
    }
    // 看堆中最大的元素是谁
    public E findMax(){
        if(data.getSize() == 0)
            throw new IllegalArgumentException("Can not findMax when the heap is empty.");
        return data.get(0);
    }
    // 取出堆中最大的元素
    public E extractMax(){
        E ret = findMax();

        data.swap(0, data.getSize() - 1);
        data.removeLast();
        shiftDown(0);

        return ret;
    }

    private void shiftDown(int k) {
        while ( leftChild(k) < data.getSize()){
            int j = leftChild(k);
            // 右节点大于左节点元素的值
            if(j + 1 < data.getSize() && data.get(j + 1).compareTo(data.get(j)) > 0)
                // j 的值就是右孩子的下标
                j++;
            // 否则的话，上边的条件不成立，j没有++操作，k这个位置的元素比它的两个孩子节点的元素的值都要大，下沉结束
            if(data.get(k).compareTo(data.get(j)) >= 0)
                break;

            data.swap(k, j);
            k = j;
        }
    }

    public E replace(E e){
        E ret = findMax();

        data.set(0, e);
        shiftDown(0);

        return ret;
    }


    @Test
    public void test()
    {
        int n = 1000000;
        MaxHeap<Integer> maxHeap = new MaxHeap<>();
        Random random = new Random();
        for(int i= 0; i < n; i++){
            maxHeap.add(random.nextInt(Integer.MAX_VALUE));
        }
        int[] arr = new int[n];
        for(int i= 0; i < n; i++){
            arr[i] = maxHeap.extractMax();
        }
        for(int i= 1; i < n; i++){
            if(arr[i -1] < arr[i]){
                System.out.println("==================>>> error");
            }
            System.out.println("------");
        }
    }
    private static double testHeap(Integer[] data, boolean isHeapify){
        long startTime = System.nanoTime();

        MaxHeap<Integer> maxHeap;
        if(isHeapify){
            maxHeap = new MaxHeap<>(data);
        }else{
            maxHeap = new MaxHeap<>();
            for(int num : data){
                maxHeap.add(num);
            }
        }
        int arr[] = new int[data.length];
        for(int i = 0; i< data.length; i++){
            arr[i] = maxHeap.extractMax();
        }
        for(int i= 1; i < data.length; i++) {
            if (arr[i - 1] < arr[i]) {
                System.out.println("==================>>> error");
            }
        }
        System.out.println("Test success.");

        long endTime = System.nanoTime();
        return (endTime - startTime) / 1000000000.0;
    }
    public static void main(String[] args) {
        int n = 1000000;
        MaxHeap<Integer> maxHeap = new MaxHeap<>();
        Random random = new Random();
        Integer[] data = new Integer[n];
        for(int i= 0; i < n; i++){
            data[i] = random.nextInt(Integer.MAX_VALUE);
        }

        double time1 = testHeap(data, false);
        System.out.println("Without heapify :"+time1);
        double time2 = testHeap(data, true);
        System.out.println("With heapify :"+time2);
    }

}
