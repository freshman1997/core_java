package com.yuan.cn.leetcode;

import org.junit.Test;


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
}
