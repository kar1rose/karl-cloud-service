package org.karl.leetcode;

import java.util.Arrays;

/**
 * 排序算法
 *
 * @author KARL ROSE
 * @date 2020/7/14 16:24
 **/
public class SortType {

    /**
     * 选择排序
     * 每次选择最小/最大的交换位置
     **/
    public int[] chooseSort(int[] nums) {
        int len = nums.length;

        for (int i = 0; i < len - 1; i++) {
            int min = nums[i];
            int index = i;
            for (int j = i + 1; j < len; j++) {
                if (nums[j] < min) {
                    min = nums[j];
                    index = j;
                }
            }
            nums[index] = nums[i];
            nums[i] = min;

        }
        return nums;
    }

    /**
     * 快速排序
     * 双指针
     **/
    public int[] sort(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
        return nums;
    }

    public void quickSort(int[] nums, int left, int right) {
        if (left < right) {
            //分区下标
            int j = partition(nums, left, right);
            quickSort(nums, left, j - 1);
            quickSort(nums, j + 1, right);
        }
    }

    public int partition(int[] nums, int left, int right) {
        int key = nums[left];
        int oldLeft = left;

        while (left < right) {
            while (left < right && nums[right] >= key) {
                right--;
            }
            while (left < right && nums[left] <= key) {
                left++;
            }
            if (left < right) {
                //交换位置
                int tmp = nums[left];
                nums[left] = nums[right];
                nums[right] = tmp;
            }
        }
        if (left == right) {
            nums[oldLeft] = nums[left];
            nums[left] = key;
        }
        return left;

    }

    public static void main(String[] args) {
        SortType sortType = new SortType();
//        System.out.println(Arrays.toString(sortType.chooseSort(new int[]{3, 2, 1})));
        System.out.println(Arrays.toString(sortType.sort(new int[]{7, 5, 4, 6, 8, 9, 3, 2})));
    }
}
