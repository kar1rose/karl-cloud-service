package org.karl.leetcode;


import java.lang.reflect.Array;
import java.util.*;

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

        int[] res = new int[len];
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
     * 112. 路径总和
     * 给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
     **/
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return root.val == sum;
        }
        return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);

    }

    /**
     * 27. 移除元素
     * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
     * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
     * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
     **/
    public int removeElement(int[] nums, int val) {
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != val) {
                nums[i] = nums[j];
                i++;
            }
        }
        return i;

    }

    /**
     * 28. 实现 strStr()
     * 实现 strStr() 函数。
     * 给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回  -1。
     **/
    public int strStr(String haystack, String needle) {
        if (needle.length() > haystack.length()) {
            return -1;
        }
        if (needle.length() == 0) {
            return 0;
        }
        for (int i = 0; i < haystack.length() - needle.length() + 1; i++) {
            if (haystack.charAt(i) == needle.charAt(0) && haystack.startsWith(needle, i)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 16.11. 跳水板
     * 你正在使用一堆木板建造跳水板。有两种类型的木板，其中长度较短的木板长度为shorter，长度较长的木板长度为longer。你必须正好使用k块木板。编写一个方法，生成跳水板所有可能的长度。
     * 返回的长度需要从小到大排列。
     **/
    public int[] divingBoard(int shorter, int longer, int k) {
        if (k == 0) {
            return new int[0];
        }
        if (shorter == longer) {
            return new int[]{shorter * k};
        }
        //结果是k+1种
        int[] ans = new int[k + 1];
        for (int i = 0; i < k + 1; i++) {
            ans[i] = shorter * (k - i) + longer * i;
        }
        return ans;
    }

    /**
     * 35. 搜索插入位置
     * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     * 你可以假设数组中无重复元素。
     **/
    public int searchInsert(int[] nums, int target) {
        if (target > nums[nums.length - 1]) {
            return nums.length;
        }
        //暴力
        /*for (int i = 0; i < nums.length; i++) {
            if (target <= nums[i]) {
                return i;
            }
        }
        return 0;*/
        //二分查找
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] >= target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    /**
     * 38. 外观数列
     * 给定一个正整数 n（1 ≤ n ≤ 30），输出外观数列的第 n 项。
     **/
    public String countAndSay(int n) {
        String ans = "1";
        if (n == 1) {
            return ans;
        }
        for (int i = 2; i <= n; i++) {
            ans = next(ans);
        }
        return ans;
    }

    public String next(String pre) {
        StringBuilder next = new StringBuilder();
        int index = 0;
        Character tmp = null;
        for (int i = 0; i < pre.length(); i++) {
            char c = pre.charAt(i);
            if (tmp == null) {
                tmp = c;
                index++;
            } else {
                if (tmp == c) {
                    //栈顶是该元素则计数器+1
                    index++;
                } else {
                    //栈顶不是该元素，弹出，计数
                    next.append(index).append(tmp.toString());
                    tmp = c;
                    index = 1;
                }
            }
            if (i == pre.length() - 1) {
                next.append(index).append(tmp.toString());
            }
        }
        return next.toString();
    }

    /**
     * 面试题 17.13. 恢复空格
     * 哦，不！你不小心把一个长篇文章中的空格、标点都删掉了，并且大写也弄成了小写。像句子"I reset the computer. It still didn’t boot!"已经变成了"iresetthecomputeritstilldidntboot"。在处理标点符号和大小写之前，你得先把它断成词语。当然了，你有一本厚厚的词典dictionary，不过，有些词没在词典里。假设文章用sentence表示，设计一个算法，把文章断开，要求未识别的字符最少，返回未识别的字符数。
     * <p>
     * 注意：本题相对原题稍作改动，只需返回未识别的字符数
     **/
    public int respace(String[] dictionary, String sentence) {


        return 0;
    }

    /**
     * 350. 两个数组的交集 II
     **/
    public int[] intersect(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            return intersect(nums2, nums1);
        }
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int num : nums1) {
            int count = map.getOrDefault(num, 0) + 1;
            map.put(num, count);
        }
        int[] intersection = new int[nums1.length];
        int index = 0;
        for (int num : nums2) {
            int count = map.getOrDefault(num, 0);
            if (count > 0) {
                intersection[index++] = num;
                count--;
                if (count > 0) {
                    map.put(num, count);
                } else {
                    map.remove(num);
                }
            }
        }
        return Arrays.copyOfRange(intersection, 0, index);
    }

    /**
     * 120. 三角形最小路径和
     * 给定一个三角形，找出自顶向下的最小路径和。每一步只能移动到下一行中相邻的结点上。
     * <p>
     * 相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。
     **/
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[] f = new int[n];
        f[0] = triangle.get(0).get(0);
        for (int i = 1; i < n; i++) {
            f[i] = f[i - 1] + triangle.get(i).get(i);
            for (int j = i - 1; j > 0; --j) {
                f[j] = Math.min(f[j - 1], f[j]) + triangle.get(i).get(j);
            }
            f[0] += triangle.get(i).get(0);
        }
        int min = f[0];
        for (int i = 1; i < n; i++) {
            min = Math.min(f[i], min);
        }
        return min;
    }

    /**
     * 96. 不同的二叉搜索树
     * 给定一个整数 n，求以 1 ... n 为节点组成的二叉搜索树有多少种？
     * <p>
     * G(n): 长度为 nn 的序列能构成的不同二叉搜索树的个数
     * F(i,n): 以 i 为根、序列长度为 n 的不同二叉搜索树个数 (1 ≤i ≤ n)(1≤i≤n)
     **/
    public int numTrees(int n) {
        int[] G = new int[n + 1];
        G[0] = 1;
        G[1] = 1;

        for (int i = 2; i <= n; ++i) {
            for (int j = 1; j <= i; ++j) {
                G[i] += G[j - 1] * G[i - j];
            }
        }
        return G[n];
    }

    /**
     * 53. 最大子序和
     * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     **/
    public int maxSubArray(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int ans = nums[0];
        int leftMax = nums[0];
        for (int i = 1; i < nums.length; i++) {
            leftMax = Math.max(leftMax + nums[i], nums[i]);
            ans = Math.max(ans, leftMax);
        }
        return ans;

    }

    /**
     * 167. 两数之和 II - 输入有序数组
     * 给定一个已按照升序排列 的有序数组，找到两个数使得它们相加之和等于目标数。
     * 函数应该返回这两个下标值 index1 和 index2，其中 index1 必须小于 index2。
     * 自己写出来哒
     **/
    public int[] twoSum(int[] numbers, int target) {
        int i = 0, j = numbers.length - 1;
        while (i < j) {
            if (numbers[i] + numbers[j] > target) {
                j--;
            } else if (numbers[i] + numbers[j] < target) {
                i++;
            } else if (numbers[i] + numbers[j] == target) {
                break;
            }
        }

        return new int[]{i + 1, j + 1};
    }

    /**
     * 58. 最后一个单词的长度
     * 给定一个仅包含大小写字母和空格 ' ' 的字符串 s，返回其最后一个单词的长度。如果字符串从左向右滚动显示，那么最后一个单词就是最后出现的单词。
     * <p>
     * 如果不存在最后一个单词，请返回 0 。
     * <p>
     * 说明：一个单词是指仅由字母组成、不包含任何空格字符的 最大子字符串。
     **/
    public int lengthOfLastWord(String s) {
        String[] strings = s.split(" ");
        if (strings.length == 0) {
            return 0;
        }
        return strings[strings.length - 1].length();
    }

    /**
     * 66. 加一
     * 给定一个由整数组成的非空数组所表示的非负整数，在该数的基础上加一。
     * <p>
     * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
     * <p>
     * 你可以假设除了整数 0 之外，这个整数不会以零开头。
     **/
    public int[] plusOne(int[] digits) {
        int len = digits.length;
        if (digits[len - 1] != 9) {
            digits[len - 1]++;
            return digits;
        }

        int nineNum = 0;
        for (int digit : digits) {
            if (digit == 9) {
                nineNum++;
            }
        }
        if (nineNum == len) {
            //全是9 会增加数组长度
            int[] ans = new int[len + 1];
            ans[0] = 1;
            return ans;
        } else {
            int tmp = 1;
            for (int i = len - 1; i >= 0; i--) {
                if (tmp == 0) {
                    break;
                }
                digits[i] += tmp;
                if (digits[i] == 10) {
                    digits[i] = 0;
                } else {
                    tmp = 0;
                }
            }
            return digits;
        }
    }

    public String addBinary(String a, String b) {
        return Integer.toBinaryString(Integer.parseInt(a, 2) + Integer.parseInt(b, 2));
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

//        print(leetcode.strStr("mississippi", "issip"));
//        print(Arrays.toString(leetcode.divingBoard(1, 2, 3)));
//        print(leetcode.searchInsert(new int[]{1, 3, 4, 5, 6}, 7));
//        print(leetcode.next("1211"));
//        print(leetcode.numTrees(4));
//        print(leetcode.maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
//        print(Arrays.toString(leetcode.twoSum(new int[]{2, 7, 11, 15}, 9)));
        print(Arrays.toString(leetcode.plusOne(new int[]{5, 2, 2, 6, 5, 7, 1, 9, 0, 3, 8, 6, 8, 6, 5, 2, 1, 8, 7, 9, 8, 3, 8, 4, 7, 2, 5, 8, 9})));
    }

}
