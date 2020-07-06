package org.karl.leetcode;

import com.alibaba.fastjson.JSON;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

/**
 * @author KARL ROSE
 * @date 2020/6/8 16:21
 **/
public class Leetcode {

    public static void print(Object str) {
        System.out.println(str);
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

    /**
     * 快速排序
     **/
    public int[] quickSort(int[] arr, int begin, int end) {
        int len = arr.length;
        if (len > 1) {
            int tmp = arr[0];
            int i = begin;
            int j = end;
            while (i < j) {
                while (i < j && arr[j] > tmp) {
                    j--;
                }
                arr[i] = arr[j];
//                while (i < end && arr[i] >= tmp) {
//                    i++;
//                }
                arr[j] = arr[i];
            }
            arr[i] = tmp;
            quickSort(arr, begin, i - 1);
            quickSort(arr, i + 1, end);
        } else {
            return arr;
        }
        return arr;

    }

    /**
     * 718. 最长重复子数组
     * 给两个整数数组 A 和 B ，返回两个数组中公共的、长度最长的子数组的长度
     **/
    public int findLength(int[] A, int[] B) {
        int n = A.length, m = B.length;
        int ret = 0;
        int len;
        for (int i = 0; i < n; i++) {
            //A从索引i处开始至结束与B的数组长度的最小值
            len = Math.min(m, n - i);
            ret = Math.max(ret, maxLength(A, B, i, 0, len));
        }
        for (int i = 0; i < m; i++) {
            //B从索引i处开始至结束与A的数组长度的最小值
            len = Math.min(n, m - i);
            ret = Math.max(ret, maxLength(A, B, 0, i, len));
        }
        return ret;
    }

    public int maxLength(int[] A, int[] B, int addA, int addB, int len) {
        int ret = 0, k = 0;
        for (int i = 0; i < len; i++) {
            if (A[addA + i] == B[addB + i]) {
                k++;
            } else {
                k = 0;
            }
            ret = Math.max(ret, k);
        }
        return ret;
    }

    /**
     * 378. 有序矩阵中第K小的元素
     * 给定一个 n x n 矩阵，其中每行和每列元素均按升序排序，找到矩阵中第 k 小的元素。
     * 请注意，它是排序后的第 k 小元素，而不是第 k 个不同的元素
     **/
    public int kthSmallest(int[][] matrix, int k) {
        int rows = matrix.length, columns = matrix[0].length;
        int[] sorted = new int[rows * columns];
        int index = 0;
        for (int[] row : matrix) {
            for (int num : row) {
                sorted[index++] = num;
            }
        }
        Arrays.sort(sorted);
        return sorted[k - 1];
    }

    /**
     * 108. 将有序数组转换为二叉搜索树
     * 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
     * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
     **/
    public TreeNode sortedArrayToBST(int[] nums) {

        return helper(nums, 0, nums.length - 1);
    }

    public TreeNode helper(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        int mid = (left + right) / 2;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = helper(nums, left, mid - 1);
        node.right = helper(nums, mid + 1, right);
        return node;

    }

    /**
     * 21. 合并两个有序链表
     * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
     **/
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {

        ListNode root = new ListNode(-1);
        ListNode pre = root;
        while (l1 != null && l2 != null) {
            ListNode next = new ListNode();
            if (l1.val <= l2.val) {
                next.val = l1.val;
                l1 = l1.next;
            } else {
                next.val = l2.val;
                l2 = l2.next;
            }
            pre.next = next;
            pre = next;
        }

        pre.next = l1 == null ? l2 : l1;
        return root.next;
    }

    /**
     * 32. 最长有效括号
     * 给定一个只包含 '(' 和 ')' 的字符串，找出最长的包含有效括号的子串的长度。
     **/
    public int longestValidParentheses(String s) {
        int maxans = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.empty()) {
                    stack.push(i);
                } else {
                    maxans = Math.max(maxans, i - stack.peek());
                }
            }
        }
        return maxans;

    }

    /**
     * 26. 删除排序数组中的重复项
     * 给定一个排序数组，你需要在 原地 删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
     * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
     **/
    public int removeDuplicates(int[] nums) {
        if (nums.length <= 1) {
            return nums.length;
        }
        int index = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[index]) {
                index++;
                nums[index] = nums[i];
            }
            if (nums[index] == nums[nums.length - 1]) {
                break;
            }
        }
        return index + 1;
    }

    /**
     * 44. 通配符匹配
     * 给定一个字符串 (s) 和一个字符模式 (p) ，实现一个支持 '?' 和 '*' 的通配符匹配。
     **/
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int i = 1; i <= n; ++i) {
            if (p.charAt(i - 1) == '*') {
                dp[0][i] = true;
            } else {
                break;
            }
        }
        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (p.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                } else if (p.charAt(j - 1) == '?' || s.charAt(i - 1) == p.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }
        return dp[m][n];
    }

    /**
     * 63. 不同路径 II
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
     * 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
     **/
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        //m*n的二维数组
        int m = obstacleGrid.length, n = obstacleGrid[0].length;
        int[] f = new int[n];
        //出发位置是否有效
        f[0] = obstacleGrid[0][0] == 0 ? 1 : 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (obstacleGrid[i][j] == 1) {
                    f[j] = 0;
                    continue;
                }
                if (j - 1 >= 0 && obstacleGrid[i][j - 1] == 0) {
                    f[j] += f[j - 1];
                }
            }
        }
        return f[m - 1];
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
//        System.out.println(Arrays.toString(leetcode.sort(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6})));
//        int[] arr = new int[]{23, 45, 17, 11, 13, 89, 72, 26, 3, 17, 11, 13};
//        print(leetcode.quickSort(arr, 0, arr.length - 1));
//        int[][] arr = new int[][]{{1, 5, 9}, {10, 11, 13}, {12, 13, 15}};
//        print(leetcode.kthSmallest(arr, 2));

//        print(leetcode.removeDuplicates(new int[]{0, 0, 1, 1, 1, 2, 2, 3, 4, 5, 5, 6, 7, 7, 7, 8, 8, 8, 8, 8}));
//        print(leetcode.isMatch("abcde", "a?b*"));
//        print(leetcode.uniquePathsWithObstacles(new int[][]{{0, 0}, {0, 0}}));

        int a = 0;
        for (int i = 0; i < 99; i++) {

        }
        System.out.println(a);
    }

}
