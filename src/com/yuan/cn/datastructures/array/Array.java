package com.yuan.cn.datastructures.array;

import java.util.Collection;

public class Array<T> {

    private Object data[];
    private int size = 0;
    private int capacity;

    public Array(int capacity) {
        this.capacity = capacity;
        data = new Object[capacity];
    }
    public Array(T[] arr){
        data = new Object[arr.length * 2];
        System.arraycopy(arr,0,data,0,arr.length);
        size = arr.length;
        capacity = arr.length * 2;
    }
    public Array() {
        this(5);
    }
    public int getSize()
    {
        return this.size;
    }
    public int getCapacity() {
        return this.data.length;
    }
    public boolean isEmpty()
    {
        return this.size == 0;
    }
    private boolean isFull(){
        if(size == data.length)
        {
            return true;
        }
        return false;
    }
    public void addLast(T item){
       add(size, item);
    }
    public void addFirst(T item){
        add(0, item);
    }
    public void add(int index, T item) {

        if(isFull()){
            Object os[] = new Object[(data.length >> 1) + data.length];
            System.arraycopy(data, 0, os, 0, size);
            data = os;
        }
        if(index < 0 || index > this.size){
            throw new IllegalArgumentException("AddLast failed, Require index > 0 and index <= size.");
        }
        for(int i = size - 1; i >=index;i--){
            data[i + 1] = data[i];
        }
        data[index] = item;
        size ++;
    }
    public T get(int index){
        if(index < 0 || index > size)
            throw new IllegalArgumentException("Get failed, index is illegal");
        return (T) data[index];
    }
    public void set(int index, T item){
        if(index < 0 || index > size)
            throw new IllegalArgumentException("Get failed, index is illegal");
        data[index] = item;
    }
    public boolean contains(T item) {
        for(Object t : data){
            if(t == item) {
                return true;
            }
        }
        return false;
    }
    public int find(T item){
        for(int i =0; i< size; i++)
        {
            if(data[i] == item)
                return i;
        }
        return -1;
    }
    public void removeElement(T item){
        int index = find(item);
        if(index != -1)
        {
            remove(index);
        }
    }
    public T remove(int index){
        if(index < 0 || index >= size){
            throw new IllegalArgumentException("Removed failed, index is illegal.");
        }
        // 要删除的元素
        T ret = (T) data[index];
        for(int i= index; i < size; i++){
            data[i] = data[i+1];
        }
        size --;
        if(size == getCapacity() / 4 && getCapacity() / 2 != 0)
        {
            reSize(getCapacity() / 2);
        }
        return ret;
    }
    public T removeFirst(){
        return remove(0);
    }
    public T removeLast(){return remove(size - 1);}
    public void removeAll(){
        data = new Object[this.capacity];
        size = 0;
    }
    public void add(T... items){
        if((items.length+size) > capacity){
           arrayCopy(items);
           return;
        }
        for(T t : items){
            addLast(t);
        }
    }
    private void arrayCopy(Object[] array){
        if((array.length + size) > capacity){
            Object dest[] = new Object[capacity + array.length];
            capacity = size + array.length+1;
            System.arraycopy(data ,0, dest, 0 , size);
            System.arraycopy(array, 0, dest, size, array.length);
            data = dest;
            size = size + array.length;
        }
    }
    public void add(Collection<T> collections){
        Object[] objects = collections.toArray();
        if((objects.length+size) > capacity){
            arrayCopy(objects);
            return;
        }
        for(Object t : objects){
            addLast((T) t);
        }
    }

    public T getLast()
    {
        return get(size -1);
    }
    public T getFirst()
    {
        return get(0);
    }
    private void reSize(int newCapacity)
    {
        T[] newData = (T[]) new Object[newCapacity*2 + 1];
        for(int i =0; i< size; i++){
            newData[i] = (T) data[ i];
        }
        data = newData;
    }

    public void swap(int index1, int index2){
        if(index1 < 0|| index1 >= size || index2 < 0|| index2 > size)
            throw new IllegalArgumentException("Index is illegal.");

        T t = (T) data[index1];
        data[index1] = data[index2];
        data[index2] = t;
    }
    @Override
    public String toString() {
         StringBuilder resource = new StringBuilder();
         resource.append(String.format("Array: size = %d, capacity = %d\n", this.size, this.data.length));
         resource.append('[');
         for(int i = 0; i< size; i++)
         {
             resource.append(data[i]);
             if(i != size-1)
                 resource.append(", ");
         }
         resource.append(']');
         return resource.toString();
    }
}
