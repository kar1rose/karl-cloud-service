package org.karl.leetcode;

/**
 * @author KARL ROSE
 * @date 2020/5/14 11:16
 **/
public class SingleNum {

    public static void main(String[] args) {
//        System.out.println(singleNum(new int[]{4, 1, 2, 1, 2}));
        System.out.println(subarraySum(new int[]{0, 0, 0}, 0));
    }

    private static int singleNum(int[] nums) {
        int ans = nums[0];
        System.out.println(ans);
        if (nums.length > 1) {
            for (int i = 1; i < nums.length; i++) {
                ans = ans ^ nums[i];
                System.out.println(ans);
            }
        }
        return ans;
    }


    private static int subarraySum(int[] nums, int k) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            int sum = nums[i];
            if (sum == k) {
                count++;
            }
            for (int j = i+1; j < nums.length; j++) {
                sum += nums[j];
                if (sum == k) {
                    count++;
                }
            }
        }
        return count;
    }
}
