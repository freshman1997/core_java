package com.yuan.cn.datastructures.sortor;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortUtils {

    /**
     * 冒泡排序
     * @param arr 准备排序的数组
     * @param <T> 实现了 Comparable接口的所有类型
     */
    public static <T extends Comparable<? super T>> void bubbleSort(@NotNull T[] arr){
        assert arr != null;
        for (int i = 0; i < arr.length -1; i++) {
            for (int i1 = i+1; i1 < arr.length; i1++) {
                if(arr[i].compareTo(arr[i1]) > 0){
                    T tmp = arr[i];
                    arr[i] = arr[i1];
                    arr[i1] = tmp;
                }
            }
        }
    }

    /**
     * 插入排序     插入排序由 N-1 趟排序组成 复杂度：O(N^2)
     * @param arr 准备排序的数组
     * @param <T> 实现了 Comparable接口的所有类型
     */
    public static <T extends Comparable<? super T>> void insertionSort(@NotNull T[] arr){
        assert arr != null;
        int j;
        for (int i = 0; i < arr.length; i++) {
            T tmp = arr[i];
            for (j =  i; j > 0 && tmp.compareTo(arr[j-1]) < 0; j--)
                arr[j] = arr[j -1];
            arr[j] = tmp;
        }
    }

    /**
     * 希尔排序   也叫缩减增量排序
     * 名称源于他的发明者 Donald Shell，该算法是冲破二次时间屏障的第一批算法之一，不过，知道它被发明的若干年后才证明它的亚二次时间界
     * 它通过比较相距一定的间隔的元素来工作；各趟比较所用的距离随着算法的进行而减少，直到只比较相邻元素的最后一趟为止。
     * @param arr 准备排序的数组
     * @param <T> 实现了 Comparable接口的所有类型
     */
    public static <T extends Comparable<? super T>> void shellSort(@NotNull T[] arr){
        assert arr != null;
        int j;
        for (int gap = arr.length / 2; gap > 0; gap /= 2){
            for(int i = gap; i < arr.length; i++){
                T t = arr[i];
                for(j = i; j >= gap && t.compareTo(arr[j - gap]) < 0; j -= gap){
                    arr[j] = arr[j - gap];
                }
                arr[j] = t;
            }
        }
    }
    /**
     * 堆排序    堆排序由 N-1 趟排序组成 复杂度：O(N log N)时间排序
     * @param arr 准备排序的数组
     * @param <T> 实现了 Comparable接口的所有类型
     */
    public static <T extends Comparable<? super T>> void heapSort(@NotNull T[] arr){
        for (int i = arr.length / 2 -1; i >= 0; i--) // build heap
            percDown(arr, i, arr.length);
        for (int i = arr.length -1; i > 0; i--)
        {
            swapReferences(arr, 0, i); // deleteMax
            percDown(arr, 0, i);
        }
    }
    // 待实现
    private static <T> void swapReferences(T[] arr, int i, int i1) {
    }
    private static int leftChild(int i){
        return 2 * i + 1;
    }
    private static <T extends Comparable<? super T> > void percDown(T[] arr, int i, int n){
        int child;
        T t;
        for(t = arr[i]; leftChild(i) < n; i = child){
            child = leftChild(i);
            if(child != n -1 && arr[child].compareTo(arr[child + 1]) < 0)
                child++;
            if(t.compareTo(arr[child]) < 0)
                arr[i] = arr[child];
            else
                break;
        }
    }
    public static <T extends Comparable<? super T>> void quickSort(@NotNull T[] arr){
        if(arr.length > 1){
            List<T> smaller = new ArrayList<>();
            List<T> same = new ArrayList<>();
            List<T> larger = new ArrayList<>();
            T chosenItem = arr[arr.length / 2];
            for (T t : arr) {
                if( t.compareTo(chosenItem) < 0)
                    smaller.add(t);
                else if(t.compareTo(chosenItem) > 0)
                    larger.add(t);
                else
                    same.add(t);
            }
            Object[] objects = smaller.toArray();
            Arrays.sort(objects);
            Object[] objects1 = larger.toArray();
            Arrays.sort(objects1);
            for (int i = 0; i < objects.length; i++)
                arr[i] = (T) objects[i];
            for (int i = 0; i < same.size(); i++)
                arr[i] = same.get(i);
            for (int i = 0; i < objects1.length; i++)
                arr[i] = (T) objects1[i];
        }
    }
    public static void main(String[] args) {
        Integer[] arr = {1, 3, 2, 5, 6, 10, 8, 9, 4, 7};
        quickSort(arr);
        for (Integer integer : arr) {
            System.out.print(integer+" ");
        }
    }
}
