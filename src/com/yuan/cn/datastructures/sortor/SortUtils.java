package com.yuan.cn.datastructures.sortor;

import com.sun.istack.internal.NotNull;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

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
     * 快速排序方法
     * @param array
     * @param start
     * @param end
     * @return
     */
    public static int[] QuickSort(int[] array, int start, int end) {
        if (start < 0 || end >= array.length || start > end) return null;
        int smallIndex = partition(array, start, end);
        if (smallIndex > start)
            QuickSort(array, start, smallIndex - 1);
        if (smallIndex < end)
            QuickSort(array, smallIndex + 1, end);
        return array;
    }
    /**
     * 快速排序算法——partition
     * @param array
     * @param start
     * @param end
     * @return
     */
    public static int partition(int[] array, int start, int end) {
        int pivot = (int) (start + Math.random() * (end - start + 1));
        int smallIndex = start - 1;
        swap(array, pivot, end);
        for (int i = start; i <= end; i++)
            if (array[i] <= array[end]) {
                smallIndex++;
                if (i > smallIndex)
                    swap(array, i, smallIndex);
            }
        return smallIndex;
    }

    /**
     * 交换数组内两个元素
     * @param array
     * @param i
     * @param j
     */
    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * 归并排序
     *
     * @param array
     * @return
     */
    public static int[] MergeSort(int[] array) {
        if (array.length < 2) return array;
        int mid = array.length / 2;
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);
        return merge(MergeSort(left), MergeSort(right));
    }
    /**
     * 归并排序——将两段排序好的数组结合成一个排序数组
     *
     * @param left
     * @param right
     * @return
     */
    public static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        for (int index = 0, i = 0, j = 0; index < result.length; index++) {
            if (i >= left.length)
                result[index] = right[j++];
            else if (j >= right.length)
                result[index] = left[i++];
            else if (left[i] > right[j])
                result[index] = right[j++];
            else
                result[index] = left[i++];
        }
        return result;
    }


    //声明全局变量，用于记录数组array的长度；
    static int len;
    /**
     * 堆排序算法
     *       堆排序（Heapsort）是指利用堆这种数据结构所设计的一种排序算法。堆积是一个近似完全二叉树的结构，并同时满足堆积的性质：即子结点的键值或索引总是小于（或者大于）它的父节点。
     *
     *       7.1 算法描述
     *
     *       将初始待排序关键字序列(R1,R2….Rn)构建成大顶堆，此堆为初始的无序区；
     *       将堆顶元素R[1]与最后一个元素R[n]交换，此时得到新的无序区(R1,R2,……Rn-1)和新的有序区(Rn),且满足R[1,2…n-1]<=R[n]；
     *       由于交换后新的堆顶R[1]可能违反堆的性质，因此需要对当前无序区(R1,R2,……Rn-1)调整为新堆，然后再次将R[1]与无序区最后一个元素交换，得到新的无序区(R1,R2….Rn-2)和新的有序区(Rn-1,Rn)。不断重复此过程直到有序区的元素个数为n-1，则整个排序过程完成。
     *
     *
     * @param array
     * @return
     */
    public static int[] HeapSort(int[] array) {
        len = array.length;
        if (len < 1) return array;
        //1.构建一个最大堆
        buildMaxHeap(array);
        //2.循环将堆首位（最大值）与末位交换，然后在重新调整最大堆
        while (len > 0) {
            swap(array, 0, len - 1);
            len--;
            adjustHeap(array, 0);
        }
        return array;
    }
    /**
     * 建立最大堆
     *
     * @param array
     */
    public static void buildMaxHeap(int[] array) {
        //从最后一个非叶子节点开始向上构造最大堆
        for (int i = (len/2 - 1); i >= 0; i--) { //感谢 @让我发会呆 网友的提醒，此处应该为 i = (len/2 - 1)
            adjustHeap(array, i);
        }
    }
    /**
     * 调整使之成为最大堆
     *
     * @param array
     * @param i
     */
    public static void adjustHeap(int[] array, int i) {
        int maxIndex = i;
        //如果有左子树，且左子树大于父节点，则将最大指针指向左子树
        if (i * 2 < len && array[i * 2] > array[maxIndex])
            maxIndex = i * 2;
        //如果有右子树，且右子树大于父节点，则将最大指针指向右子树
        if (i * 2 + 1 < len && array[i * 2 + 1] > array[maxIndex])
            maxIndex = i * 2 + 1;
        //如果父节点不是最大值，则将父节点与最大值交换，并且递归调整与父节点交换的位置。
        if (maxIndex != i) {
            swap(array, maxIndex, i);
            adjustHeap(array, maxIndex);
        }
    }

    /**
     * 计数排序
     *计数排序（Counting Sort）
     * 计数排序的核心在于将输入的数据值转化为键存储在额外开辟的数组空间中。 作为一种线性时间复杂度的排序，计数排序要求输入的数据必须是有确定范围的整数。
     *
     * 计数排序(Counting sort)是一种稳定的排序算法。计数排序使用一个额外的数组C，其中第i个元素是待排序数组A中值等于i的元素的个数。然后根据数组C来将A中的元素排到正确的位置。它只能对整数进行排序。
     *
     * 8.1 算法描述
     *
     * 找出待排序的数组中最大和最小的元素；
     * 统计数组中每个值为i的元素出现的次数，存入数组C的第i项；
     * 对所有的计数累加（从C中的第一个元素开始，每一项和前一项相加）；
     * 反向填充目标数组：将每个元素i放在新数组的第C(i)项，每放一个元素就将C(i)减去1。
     *
     * @param array
     * @return
     */
    public static int[] CountingSort(int[] array) {
        if (array.length == 0) return array;
        int bias, min = array[0], max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max)
                max = array[i];
            if (array[i] < min)
                min = array[i];
        }
        bias = 0 - min;
        int[] bucket = new int[max - min + 1];
        Arrays.fill(bucket, 0);
        for (int i = 0; i < array.length; i++) {
            bucket[array[i] + bias]++;
        }
        int index = 0, i = 0;
        while (index < array.length) {
            if (bucket[i] != 0) {
                array[index] = i - bias;
                bucket[i]--;
                index++;
            } else
                i++;
        }
        return array;
    }

    /**
     * 桶排序
     *
     * @param array
     * @param bucketSize
     * @return
     */
    public static ArrayList<Integer> BucketSort(ArrayList<Integer> array, int bucketSize) {
        if (array == null || array.size() < 2)
            return array;
        int max = array.get(0), min = array.get(0);
        // 找到最大值最小值
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) > max)
                max = array.get(i);
            if (array.get(i) < min)
                min = array.get(i);
        }
        int bucketCount = (max - min) / bucketSize + 1;
        ArrayList<ArrayList<Integer>> bucketArr = new ArrayList<>(bucketCount);
        ArrayList<Integer> resultArr = new ArrayList<>();
        for (int i = 0; i < bucketCount; i++) {
            bucketArr.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < array.size(); i++) {
            bucketArr.get((array.get(i) - min) / bucketSize).add(array.get(i));
        }
        for (int i = 0; i < bucketCount; i++) {
            if (bucketSize == 1) { // 如果带排序数组中有重复数字时  感谢 @见风任然是风 朋友指出错误
                for (int j = 0; j < bucketArr.get(i).size(); j++)
                    resultArr.add(bucketArr.get(i).get(j));
            } else {
                if (bucketCount == 1)
                    bucketSize--;
                ArrayList<Integer> temp = BucketSort(bucketArr.get(i), bucketSize);
                for (int j = 0; j < temp.size(); j++)
                    resultArr.add(temp.get(j));
            }
        }
        return resultArr;
    }


    /**
     * 基数排序
     * @param array
     * @return
     */
    public static int[] RadixSort(int[] array) {
        if (array == null || array.length < 2)
            return array;
        // 1.先算出最大数的位数；
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            max = Math.max(max, array[i]);
        }
        int maxDigit = 0;
        while (max != 0) {
            max /= 10;
            maxDigit++;
        }
        int mod = 10, div = 1;
        ArrayList<ArrayList<Integer>> bucketList = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < 10; i++)
            bucketList.add(new ArrayList<Integer>());
        for (int i = 0; i < maxDigit; i++, mod *= 10, div *= 10) {
            for (int j = 0; j < array.length; j++) {
                int num = (array[j] % mod) / div;
                bucketList.get(num).add(array[j]);
            }
            int index = 0;
            for (int j = 0; j < bucketList.size(); j++) {
                for (int k = 0; k < bucketList.get(j).size(); k++)
                    array[index++] = bucketList.get(j).get(k);
                bucketList.get(j).clear();
            }
        }
        return array;
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

    public <E extends Comparable<E>>  Stack<E> getSortedStack(Stack<E> unSortedStack){
        Stack<E>  helperStack = new Stack<>();

        while (! unSortedStack.isEmpty()) {

            E current = unSortedStack.pop();

            while (! helperStack.isEmpty() && helperStack.peek().compareTo(current) < 0){
                unSortedStack.push(helperStack.pop());
            }
            helperStack.push(current);
        }

        while (! helperStack.isEmpty()){
            unSortedStack.push(helperStack.pop());
        }

        return unSortedStack;
    }
    @Test
    public void test(){
        Stack<Integer> integerStack = new Stack<>();
        integerStack.push(1);
        integerStack.push(3);
        integerStack.push(2);
        integerStack.push(4);
        integerStack.push(5);
        integerStack.push(0);
        integerStack.push(9);
        getSortedStack(integerStack).forEach(System.out::println);
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 2, 5, 6, 10, 8, 9, 4, 7};
        HeapSort(arr);
        for (Integer integer : arr) {
            System.out.print(integer+" ");
        }
    }
}
