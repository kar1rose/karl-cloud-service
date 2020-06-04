package org.karl.leetcode;

/**
 * @author KARL ROSE
 * @date 2020/6/3 14:16
 **/
public class DayQuestion {

    public static void main(String[] args) {
        System.out.println(new21Game(6, 1, 10));
    }


    /**
     * 1.计算大于等于K的组合数
     * 2.计算小于等于N的组合数
     *
     * @param N 不超过N
     * @param K 得分超过K
     * @param W [1,W]的集合
     * @author KARL.ROSE
     * @date 2020/6/3 2:18 下午
     **/
    public static double new21Game(int N, int K, int W) {
        if (K == 0) {
            return 1.0;
        }
        double[] dp = new double[K + W];
        for (int i = K; i <= N && i < K + W; i++) {
            dp[i] = 1.0;
        }
        for (int i = K - 1; i >= 0; i--) {
            for (int j = 1; j <= W; j++) {
                dp[i] += dp[i + j] / W;
            }
        }
        return dp[0];
    }

    public static int[] productExceptSelf(int[] nums) {
        int length = nums.length;
        int[] answer = new int[length];
        answer[0] = 1;
        for (int i = 1; i < length; i++) {
            answer[i] = nums[i - 1] * answer[i - 1];
        }
        int R = 1;
        for (int i = length - 1; i >= 0; i--) {
            answer[i] = answer[i] * R;
            R *= nums[i];
        }

        return answer;

    }


}
