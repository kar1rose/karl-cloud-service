package org.karl.leetcode;

import java.util.*;

/**
 * @author KARL ROSE
 * @date 2020/6/8 16:21
 **/
public class Leetcode {

    public static void print(Object str) {
        System.out.println(str);
    }

    public static void main(String[] args) {
        Leetcode leetcode = new Leetcode();
//        String[] arr = new String[]{"c==c", "f!=a", "f==b", "b==c"};
//        print(leetcode.equationsPossible(arr));
//        print(leetcode.translateNum(1225));
//        print(Arrays.toString(leetcode.dailyTemperatures(new int[]{73, 72, 70, 71, 69, 76, 79, 73})));
//        print(leetcode.threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
//        print(leetcode.longestCommonPrefix(new String[]{"flower", "flow", "flight", "flwww", "flyure", "flwerr", "flopit", "flascd", "flr345", "fl98ug"}));
//        print(leetcode.maxScoreSightseeingPair(new int[]{1, 3, 5}));
//        print(String.valueOf(leetcode.romanToInt("LVIII")));
//        System.out.print(leetcode.isPalindrome("A man, a plan, a canal: Panama"));
        System.out.println(Arrays.toString(leetcode.sort(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6})));

    }

    public boolean equationsPossible(String[] equations) {
        int[] parent = new int[26];
        for (int i = 0; i < 26; i++) {
            parent[i] = i;
        }
        for (String str : equations) {
            if (str.charAt(1) == '=') {
                int index1 = str.charAt(0) - 'a';
                int index2 = str.charAt(3) - 'a';
                union(parent, index1, index2);
                print(Arrays.toString(parent));
            }
        }
        for (String str : equations) {
            if (str.charAt(1) == '!') {
                int index1 = str.charAt(0) - 'a';
                int index2 = str.charAt(3) - 'a';
                if (find(parent, index1) == find(parent, index2)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void union(int[] parent, int index1, int index2) {
        parent[find(parent, index1)] = find(parent, index2);
    }

    public int find(int[] parent, int index) {
        while (parent[index] != index) {
            parent[index] = parent[parent[index]];
            index = parent[index];
        }
        return index;
    }


    public int translateNum(int num) {
        String nums = String.valueOf(num);
        int p, q = 0, r = 1;
        for (int i = 0; i < nums.length(); i++) {
            p = q;
            q = r;
            if (i == 0) {
                continue;
            }
            String pre = nums.substring(i - 1, i + 1);
            if (pre.compareTo("25") <= 0 && pre.compareTo("10") >= 0) {
                r += p;
            }
        }
        return r;
    }

    /**
     * 根据每日 气温 列表，请重新生成一个列表，对应位置的输出是需要再等待多久温度才会升高超过该日的天数。如果之后都不会升高，请在该位置用 0 来代替。
     *
     * @author KARL.ROSE
     * @date 2020/6/11 12:26 下午
     **/
    public int[] dailyTemperatures(int[] T) {
        int length = T.length;
        int[] ans = new int[length];
        //双向链表
        Deque<Integer> stack = new LinkedList<>();

        for (int i = 0; i < T.length; i++) {
            while (!stack.isEmpty() && T[stack.peek()] < T[i]) {
                int preIndex = stack.pop();
                ans[preIndex] = i - preIndex;
            }
            stack.push(i);
        }
        return ans;

    }

    public List<List<Integer>> threeSum(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length - 1; j++) {
                for (int n = j + 1; n < nums.length; n++) {
                    if (nums[i] + nums[j] + nums[n] == 0) {
                        List<Integer> list = new ArrayList<>();
                        list.add(nums[i]);
                        list.add(nums[j]);
                        list.add(nums[n]);
                        if (!result.contains(list)) {
                            result.add(list);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 14. 最长公共前缀
     * 编写一个函数来查找字符串数组中的最长公共前缀。
     * 如果不存在公共前缀，返回空字符串 ""。
     **/
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        } else {
            return longestCommonPrefix(strs, 0, strs.length - 1);
        }
    }

    private String longestCommonPrefix(String[] strs, int start, int end) {
        print("start=" + start + ",end=" + end);
        if (start == end) {
            return strs[start];
        } else {
            int mid = (end - start) / 2 + start;
            String lcpLeft = longestCommonPrefix(strs, start, mid);
            String lcpRight = longestCommonPrefix(strs, mid + 1, end);
            return commonPrefix(lcpLeft, lcpRight);
        }
    }

    private String commonPrefix(String lcpLeft, String lcpRight) {
        int minLength = Math.min(lcpLeft.length(), lcpRight.length());
        for (int i = 0; i < minLength; i++) {
            if (lcpLeft.charAt(i) != lcpRight.charAt(i)) {
                return lcpLeft.substring(0, i);
            }
        }
        return lcpLeft.substring(0, minLength);
    }

    /**
     * 1014. 最佳观光组合
     * 给定正整数数组 A，A[i] 表示第 i 个观光景点的评分，并且两个景点 i 和 j 之间的距离为 j - i。
     * 一对景点（i < j）组成的观光组合的得分为（A[i] + A[j] + i - j）：景点的评分之和减去它们两者之间的距离。
     * 返回一对观光景点能取得的最高分。
     **/
    public int maxScoreSightseeingPair(int[] A) {
        int max = 0;
        int left = A[0];
        for (int j = 1; j < A.length; j++) {
            int index = A[j] - j + left;
            if (index > max) {
                max = index;
            }
            int newLeft = A[j] + j;
            if (newLeft > left) {
                left = newLeft;
            }
        }
        return max;
    }

    /**
     * 13. 罗马数字转整数
     **/
    public int romanToInt(String s) {
        int sum = 0;
        int preNum = getRomValue(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            int num = getRomValue(s.charAt(i));
            if (num <= preNum) {
                sum += preNum;
            } else {
                sum -= preNum;
            }
            preNum = num;
        }
        sum += preNum;
        return sum;
    }

    private int getRomValue(char rom) {
        switch (rom) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }

    public String intToRoman(int num) {
        String result = "";
        while (num > 0) {
            int key = num % 10;
        }
        return result;
    }

    private char getRomStr(int num) {
        switch (num) {
            case 1:
                return 'I';
            case 5:
                return 'V';
            case 10:
                return 'X';
            case 50:
                return 'L';
            case 100:
                return 'C';
            case 500:
                return 'D';
            case 1000:
                return 'M';
            default:
                return 0;
        }
    }

    /**
     * 125. 验证回文串
     * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
     **/
    public boolean isPalindrome(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                sb.append(c);
            }
        }
        String s1 = sb.toString();
        if (s1.length() == 0) {
            return true;
        }
        int mid = s1.length() / 2;
        for (int i = 0; i <= mid; i++) {
            if (s1.charAt(i) != s1.charAt(s1.length() - 1 - i)) {
                return false;
            }
        }
        return true;
    }


    /**
     * 209. 长度最小的子数组
     * 给定一个含有 n 个正整数的数组和一个正整数 s ，找出该数组中满足其和 ≥ s 的长度最小的连续子数组，并返回其长度。如果不存在符合条件的连续子数组，返回 0。
     **/
    public int minSubArrayLen(int s, int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int length = Integer.MAX_VALUE;
        int start = 0, end = 0;
        int sum = 0;

        while (end < n) {
            sum += nums[end];
            while (sum >= s) {
                length = Math.min(length, end - start + 1);
                sum -= nums[start];
                start++;

            }
            end++;
        }
        return length == Integer.MAX_VALUE ? 0 : length;
    }


    public boolean isValid(String s) {
        HashMap<Character, Character> mappings = new HashMap<>();
        mappings.put(')', '(');
        mappings.put('}', '{');
        mappings.put(']', '[');

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (mappings.containsKey(c)) {
                char topElement = stack.empty() ? '#' : stack.pop();
                if (topElement != mappings.get(c)) {
                    return false;
                }
            } else {
                stack.push(c);
            }
        }

        return stack.isEmpty();
    }

    public int findKthLargest(int[] nums, int k) {
        if (nums.length == 1) {
            return nums[0];
        }
        nums = sort(nums);
        return nums[k - 1];
    }

    /**
     * 冒泡排序 降序排列
     **/
    public int[] sort(int[] nums) {
        boolean flag;
        int len = nums.length;
        int[] res = new int[len];
        for (int i = 0; i < len; i++) {
            flag = false;
            int tmp;
            for (int j = 0; j < len - i - 1; j++) {
                if (nums[j] < nums[j + 1]) {
                    //发生交换
                    tmp = nums[j + 1];
                    nums[j + 1] = nums[j];
                    nums[j] = tmp;
                    flag = true;
                }
            }
            System.out.println("【第" + (i + 1) + "次冒泡】");
            if (!flag) {
                break;
            }
        }

        int tmp = nums[0];
        res[0] = nums[0];
        int index = 1;
        for (int i = 1; i < len; i++) {
            if (tmp != nums[i]) {
                tmp = nums[i];
                res[index] = tmp;
                index++;
            }
        }

        return res;
    }


}
