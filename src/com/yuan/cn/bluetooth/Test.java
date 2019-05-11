package com.yuan.cn.bluetooth;

import java.util.*;

public class Test {
    public static int[] twoSum(int[] nums, int target) {
        int arr[] = new int[2];
        int a;
        for(int i = 0; i< nums.length;i++){
            a=nums[i];
            for(int j = i+1; j< nums.length;j++){
                if((a+nums[j])== target){
                    arr[1]=i;
                    arr[0]=j;
                    System.out.println(i+"===>"+j);
                }
            }
        }
        return arr;
    }

    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution.ListNode node = new Solution.ListNode(2);
//        Solution.ListNode node1 = new Solution.ListNode(4);
//        Solution.ListNode node2 = new Solution.ListNode(3);
//        Solution.ListNode node8 = new Solution.ListNode(7);
//        node.next = node1;
//        node.next.next=node2;
//        node.next.next.next=node8;
//
//        Solution.ListNode node3 = new Solution.ListNode(5);
//        Solution.ListNode node4 = new Solution.ListNode(6);
//        Solution.ListNode node5 = new Solution.ListNode(4);
//        node3.next = node4;
//        node3.next.next = node5;
//        Solution.ListNode node6 = solution.addTwoNumbers(node, node3);
//        System.out.println(node6.val);
//        System.out.println(node6.next.val);
//        System.out.println(node6.next.next.val);
//        System.out.println(node6.next.next.next.val);
//        System.out.println(Integer.MAX_VALUE);
//        System.out.println(reverse(1563847412));
//        System.out.println(strStr("mississippi","issip"));
        System.out.println(threeSum(new int[]{7,5,-8,-6,-13,7,10,1,1,-4,-14,0,
                                                -1,-10,1,-13,-4,6,-11,8,-6,0,0,
                                                -5,0,11,-9,8,2,-6,4,-14,6,4,-5,
                                                0,-12,12,-13,5,-6,10,-10,0,7,-2,
                                                -5,-12,12,-9,12,-9,6,-11,1,14,8,
                                                -1,7,-13,8,-11,-11,0,0,-1,-15,3,
                                                -11,9,-7,-10,4,-2,5,-4,12,7,-8,
                                                9,14,-11,7,5,-15,-15,-4,0,0,-11,
                                                3,-15,-15,7,0,0,13,-7,-12,9,9,-3,
                                                14,-1,2,5,2,-9,-3,1,7,-12,-3,-1,
                                                1,-2,0,12,5,7,8,-7,7,8,7,-15}));
        System.out.println(Date.class.getSimpleName());
    }

    // leet code 2号
    static class Solution {
        static class ListNode {
            int val;
            ListNode next;
            ListNode(int x) { val = x; }
        }
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode dummyHead = new ListNode(0);
            ListNode p = l1, q = l2, curr = dummyHead;
            int carry = 0;
            while (p != null || q != null) {
                int x = (p != null) ? p.val : 0;
                int y = (q != null) ? q.val : 0;
                int sum = carry + x + y;
                carry = sum / 10;
                curr.next = new ListNode(sum % 10);
                curr = curr.next;
                if (p != null) p = p.next;
                if (q != null) q = q.next;
            }
            // 这是溢出了，已经比最大的数字还要大，所以进位
            if (carry > 0) {
                curr.next = new ListNode(carry);
            }
            return dummyHead.next;
        }
    }
    // leet code 7号
    public static int reverse(int x) {

        Integer i = x;
        String str = i.toString();
        int len;
        if( x < 0){
            len = str.length() - 1;
            str = str.substring(1, str.length());
            char arr[] = new char[len];
            char newChar[] = new char[len];
            for(int k = 0; k < str.length(); k++)
                arr[k] = str.charAt(k);
            int c = 0;
            for(int k = arr.length - 1;  k >= 0; k--){
                newChar[c] = arr[k];
                c++;
            }
            String s= new String(newChar);
            int maxValue = Integer.MAX_VALUE;
            Integer max = maxValue;
            if(s.length() < max.toString().length()){
                return -Integer.parseInt(s);
            }else if(s.length() == max.toString().length())
            {
                try {
                    int result = -Integer.parseInt(s);
                    return result;
                }catch (Exception e){
                    return 0;
                }
            }else{
                return 0;
            }
        }else {
            len = str.length();
            char arr[] = new char[len];
            char newChar[] = new char[len];
            for (int k = 0; k < str.length(); k++)
                arr[k] = str.charAt(k);
            int c = 0;
            for (int k = arr.length - 1; k >= 0; k--) {
                newChar[c] = arr[k];
                c++;
            }
            System.out.println(newChar);
            String s = new String(newChar);
            int maxValue = Integer.MAX_VALUE;
            Integer max = maxValue;
            if(s.length() < max.toString().length()){
                return Integer.parseInt(s);
            }else if(s.length() == max.toString().length())
            {
                try {
                    int result = Integer.parseInt(s);
                    return result;
                }catch (Exception e){
                    return 0;
                }
            }else{
                return 0;
            }
        }

    }

    // 相当于返回了一个数组
    public static String process(String value[])[] {

        for (int i = 0; i < value.length; i++) {
            value[i] = "asdsd";
        }
        return value;
    }
    // leet code 27号
    public int removeElement(int[] nums, int val) {
        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            if(num != val)
                list.add(num);
        }
        for (int i = 0; i < list.size(); i++) {
            nums[i] = list.get(i);
        }
        return list.size();
    }
    // leet code 28号
    public static int strStr(String haystack, String needle) {
        int c = 0;
        if (haystack != null && needle != null) {

                if (haystack.equals("") && needle.equals(""))
                    return 0;
                if(haystack.equals(needle))
                    return 0;
                if (!haystack.equals("") && needle.equals(""))
                    return 0;
                if (haystack.length() < needle.length())
                    return -1;
                for (int i = 0; i < haystack.length(); i++) {
                    //如果第一个匹配到了
                    if (haystack.charAt(i) == needle.charAt(c)) {
                        int t = i;
                        int t1 = c;
                        while (haystack.charAt(t) == needle.charAt(t1)) {
                            if (t1 + 1 == needle.length()) {
                                return i;
                            }
                            if (t + 1 == haystack.length() && t1 + 1 < needle.length())
                                return -1;
                            t++;
                            t1++;
                        }
                    }
                }
                return -1;
            }
            return -1;
    }
    // leet code 20号
    public boolean isValid(String s) {
        Stack<Character> chars = new Stack<>();
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(c == '(' || c == '[' || c == '{')
            {
                chars.push(c);
            }else {
                if(chars.isEmpty())
                    return false;
                Character pop = chars.pop();
                if(c == ')' && pop != '(')
                    return false;
                if(c == ']' && pop != '[')
                    return false;
                if(c == '}' &&  pop != '{')
                    return false;
            }
        }
        return chars.isEmpty();
    }
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> total = new ArrayList<>();
        if(nums!= null && nums.length != 0) {
            List<Integer> list;
            for (int i = 0; i < nums.length - 2; i++) {
                for (int i1 = i + 1; i1 < nums.length - 1; i1++) {
                    for (int i2 = i1 + 1; i2 < nums.length; i2++) {
                        if ((nums[i] + nums[i1] + nums[i2]) == 0) {
                            list = new ArrayList<>();
                            list.add(nums[i]);
                            list.add(nums[i1]);
                            list.add(nums[i2]);
                            total.add(list);
                        }
                    }
                }
            }
            for (List<Integer> list1 : total) {
                Object[] objects = list1.toArray();
                Arrays.sort(objects);
                list1.clear();
                for (Object o : objects) list1.add((Integer) o);
            }
            Object[] objects = total.toArray();
            for (int i = 0; i < objects.length-1; i++) {
                for(int j = i+1; j < objects.length; j++){
                    if(objects[i] != null) {
                        if (((List) objects[i]).get(0) == ((List) objects[j]).get(0) &&
                                ((List) objects[i]).get(1) == ((List) objects[j]).get(1) &&
                                ((List) objects[i]).get(2) == ((List) objects[j]).get(2))
                            objects[i] = null;
                    }
                }
            }
            total.clear();
            for (Object object : objects) {
                System.out.println(object);
                if(object != null)
                    total.add((List<Integer>) object);
            }
            return total;
        }
        return total;
    }
    private static void sort(Object[] objects){
        for (int i = 0; i < objects.length -1; i++) {
            for (int i1 = i+1; i1 < objects.length; i1++) {
                if ((int)objects[i] > (int)objects[i1])
                {
                    int tmp = (int) objects[i];
                    objects[i] = objects[i1];
                    objects[i1] = tmp;
                }
            }
        }
    }
}
