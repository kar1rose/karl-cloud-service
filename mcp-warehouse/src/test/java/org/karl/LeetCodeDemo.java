package org.karl;
/**
 * Created by KARL_ROSE on 2020/11/29 10:50
 */

import java.util.Arrays;
import java.util.Stack;

/**
 * @ClassName leetCodeDemo
 * @Author ROSE
 * @Date 2020/11/29 10:50
 * @Description
 **/
public class LeetCodeDemo {

    public static int[] mostCompetitive(int[] nums, int k) {
        Stack<Integer> stack = new Stack<>();
        int n = nums.length;
        for (int i = 0; i < nums.length; i++) {
            while (n - i + stack.size() > k && !stack.empty()) {
                if (nums[i] < stack.peek()) {
                    stack.pop();
                } else {
                    break;
                }
            }
            if (stack.size() < k) {
                stack.push(nums[i]);
            }
        }
        int[] res = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            res[i] = stack.pop();
        }
        return res;
    }

    public static void main(String[] args) {
//        System.out.println(new Test().minOperationsMaxProfit(new int[]{10, 10, 6, 4, 7}, 3, 8));
//        System.out.println(Arrays.toString(sortByBits(new int[]{1, 3, 5, 7, 9})));
//        System.out.println(findMinArrowShots(new int[][]{{1, 2}, {3, 4}, {5, 6}, {7, 8}}));
        System.out.println(Arrays.toString(mostCompetitive(new int[]{3, 5, 2, 6}, 2)));
    }
}
