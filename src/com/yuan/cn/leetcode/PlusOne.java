package com.yuan.cn.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class PlusOne {

    public static int[] plusOne(int[] digits) {
        if(digits[0] < 0)
            return new int[]{0};
        int a = Integer.MAX_VALUE;
        if(digits.length < Integer.toString(a).length()) {
            StringBuilder builder = new StringBuilder();
            builder.append("1");
            for (int i = 0; i < digits.length - 1; i++) {
                builder.append("0");
            }
            String zero = builder.toString();
            System.out.println(zero);
            int result = 0;
            for (int i = 0; i < digits.length; i++) {
                result += digits[i] * Integer.parseInt(zero.substring(0, zero.length() - i));
            }
            System.out.println(result);
            result += 1;
            String s = Integer.toString(result);
            int[] newArr = new int[s.length()];
            for (int i = 0; i < newArr.length; i++) {
                newArr[i] = Integer.parseInt(s.substring(i, i + 1));
            }
            return newArr;
        }else {
            int i = digits.length-2;
            digits[digits.length-1] =  digits[digits.length-1]+1;
            if(digits[digits.length-1] == 10) {
                digits[digits.length-1] = 0;
                digits[i] = digits[i] + 1;
                while (digits[i] == 10 && i > 0) {
                    digits[i] = 0;
                    i--;
                    digits[i] = digits[i] + 1;
                }
            }
            if(digits[0] == 10)
            {
                digits[0] = 0;
                int[] newArr = new int[digits.length + 1];
                newArr[0] = 1;
                for (int i1 = 0; i1 < digits.length; i1++) {
                    newArr[i1+1] = digits[i1];
                }

                return newArr;
            }
            for (int digit : digits) {
                System.out.print(digit + " ");
            }
            return digits;
        }
    }


    public static int majorityElement(int[] nums) {
        int count = 1;
        int maj = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (maj == nums[i])
                count++;
            else {
                count--;
                if (count == 0) {
                    maj = nums[i + 1];
                }
            }
        }
        return maj;
    }
    @Test
    public void testMajority(){
        int[] arr = new int[]{3,2,3};
        majorityElement(arr);
    }
    @Test
    public void test() {
        int[] arr = new int[]{9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9};
        int[] ints = plusOne(arr);
        for (int anInt : ints) {
            System.out.print(anInt + " " );
        }
    }

    @Test
    public void testDivide(){
        int dividend = -3, divisor = 1;
        int count = 0;

//        for (int i = Math.abs(m); i <= Math.abs(n); i = i + Math.abs(m)){
//            if (i == n)
//                --count;
//            count++;
//
//        }
        int res = divide(dividend, divisor);
        System.out.println(1 / 3.0);

    }

    private int divide(int dividend, int divisor) {
        if(divisor == 0)
            throw new IllegalArgumentException("divisor can not be zero.");
        int count = 0;
        if (divisor == -1 && dividend < 0 && dividend != Integer.MIN_VALUE)
            return -dividend;
        if (divisor == -1 && dividend == Integer.MIN_VALUE)
            return Integer.MAX_VALUE;
        if (divisor == 1 && dividend > 0)
            return dividend;
        if (divisor == 1 && dividend < 0 && dividend != Integer.MIN_VALUE)
            return dividend;
        if (divisor == 1 && dividend == Integer.MIN_VALUE)
            return Integer.MIN_VALUE;
        long s = dividend;
        long m = divisor;

        for (long i = Math.abs(m); i <= Math.abs(s); i = i + Math.abs(m)){

            if (i > Math.abs(s))
                --count;
            if (count == Integer.MAX_VALUE && i <= Math.abs(s) + 1)
            {
                break;
            }
            count++;
        }
        if((dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0))
            return -count;
        else
            return count;
    }

    @Test
    public void test1(){
        int[] nums = new int[]{1, 1, 2};
        System.out.println(removeDuplicates(nums));
        for (int num : nums) {
            System.out.println(num);
        }
    }
    private int lengthOfLastWord(String s) {
        if (s == null || s.equals("") || s.equals(" "))
            return 0;
        int i = 0;
        int sum = 0;
        while (i < s.length()){
            if (s.charAt(i) == ' ')
                sum++;
            i++;
        }
        if (sum == s.length())
            return 0;
        if (s.trim().contains(" ")){
            return s.trim().substring(s.trim().lastIndexOf(" ") + 1).length();
        }else {
            return s.trim().length();
        }
    }

    private String longestCommonPrefix(String[] strs) {
        if (strs.length == 0)
            return "";
        if (strs.length == 1)
            return strs[0];

        for (String str : strs) {
            if (str.equals(""))
                return "";
        }
        int l = strs[0].length();
        for (int i = 1; i < strs.length; i++) {
            if (strs[i].length() < l)
                l = strs[i].length();
        }
        int i = 0, k = 0;
        char c = strs[i].charAt(k);
        String s = "";
        while (k < strs[i].length()){
            char a = strs[++i].charAt(k);
            if (a == c)
            {
                if (i == strs.length -1){
                    i = 0;
                    s += a;
                    if (k == l-1)
                        break;
                    ++k;
                }
                c = strs[i].charAt(k);
            }else
                break;

        }
        return s;
    }
    private List<List<Integer>> generate(int numRows) {
        List<List<Integer>> lists = new ArrayList<>();
        List<Integer> list;
        for (int i = 0; i < numRows; i ++){
            list = new ArrayList<>();
            for (int j = 0; j <= i; j ++){
                //list.add(1);
                if (lists.size() >= 2 && j != 0 && j != i){
                    List<Integer> list1 = lists.get(i - 1);
                    list.add(list1.get(j-1) + list1.get(j));
                }else {
                    list.add(1);
                }
            }
            if (list.size() > 0)
                lists.add(list);
        }
        return lists;
    }
    private int removeDuplicates(int[] nums) {
        if (nums.length == 0 || nums.length == 1)
            return nums.length;
        int j = 0;
        for (int i = 1; i < nums.length; i++)
            if (nums[j] != nums[i]) nums[++j] = nums[i];
        return ++j;
    }
}
