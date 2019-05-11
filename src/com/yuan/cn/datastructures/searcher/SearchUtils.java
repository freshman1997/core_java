package com.yuan.cn.datastructures.searcher;

import java.util.Arrays;

public class SearchUtils {
    /**
     *
     * @param arr 要查找元素的数组
     * @param value 指定查询的值
     * @param <T>   任意类型
     * @return 返回指定的下标
     */
    public static <T extends Comparable<? super T> > int sequenceSearch(T[] arr, T value){
        int i = 0;
        for (T t : arr) {
            if(t.compareTo(value) == 0) {
                return i;
            }
            i ++;
        }
        return -1;
    }

    /**
     * 二分查找，在进行查找之前必须对源数组进行排序，否则无法查找
     * @param arr   待查找的数组
     * @param value 指定查找的值
     * @param <T>   任意类型的待查找的值
     * @return      返回下标
     */
    public static <T extends Comparable<? super T> > int binarySearch(T[] arr, T value){
        int mid;
        int high = arr.length -1;
        int low = 0;
        while (low <= high){
            // 不断的折半
            mid = (low + high) / 2;
            if(arr[mid].compareTo(value) > 0){
                // 如果大于中值，那么最大值移为中值的前一个
                high = mid - 1;
            }else if(arr[mid].compareTo(value) < 0){
                // 如果小于中值，那么最小值移为中值的后一个
                low = mid + 1;
            }else {
                return mid;
            }
        }
        return -1;
    }
    // 二分查找的递归版本  查找元素前必须排序好源数组，否则会死循环的，内存耗尽
    public static <T extends Comparable<? super T> > int binarySearch(T[] arr, T value, int low, int high){
        int mid = low + (high - low) / 2;
        if(arr[mid].compareTo(value) == 0)
            return mid;
        else if(arr[mid].compareTo(value) < 0)
            return binarySearch(arr, value, low, mid-1);
        else
            return binarySearch(arr, value, mid+1, high);
    }
    public static int insertionSearch(int[] a, int value, int low, int high){
        int mid = low + (value - a[low]) / (a[high] - a[low]) * (high - low);
        if(a[mid]==value)
            return mid;
        else if(a[mid]>value)
            return insertionSearch(a, value, low, mid-1);
        else
            return insertionSearch(a, value, mid+1, high);
    }

    public static void main(String[] args) {
        Integer[] arr = {1, 3, 2, 5, 6, 10, 8, 9, 4, 7};
        Arrays.sort(arr);
        System.out.println(binarySearch(arr, 5,0, arr.length -1));
    }
}
