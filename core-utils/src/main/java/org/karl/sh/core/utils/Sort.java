package org.karl.sh.core.utils;
/**
 * Created by KARL_ROSE on 2021/2/5 22:50
 */

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author karl rose
 * @date 2021/2/5 23:18
 **/
public class Sort {

    private static int count = 0;


    public static void sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int left, int right) {
        int p = arr[left];
        int start = left;
        int end = right;
        int temp;

        while (start < end) {
            count++;
            System.out.println("loop " + count + " round");
            System.out.println(Arrays.toString(arr));
            while (start < end && p <= arr[end]) {
                end--;
            }
            if (p > arr[end]) {
                temp = arr[end];
                arr[end] = arr[start];
                arr[start] = temp;
            }
            while (start < end && p >= arr[start]) {
                start++;
            }
            if (p < arr[start]) {
                temp = arr[start];
                arr[start] = arr[end];
                arr[end] = temp;
            }
        }
        if (start > left) {
            quickSort(arr, left, start - 1);
        }
        if (end < right) {
            quickSort(arr, end + 1, right);
        }

    }

    public static void bubbleSort(int[] arr) {
        int temp;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                count++;
                System.out.println("loop " + count + " round");
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
            System.out.println(Arrays.toString(arr));
        }
    }

    public static int maxScore(int[] cardPoints, int k) {
        //计算从做开始取K张的点数
        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum += cardPoints[i];
        }
        int n = cardPoints.length;
        int temp = sum;
        for (int i = n - 1; i > n - 1 - k; i--) {
            sum = Math.max(temp - cardPoints[k - (n - i)] + cardPoints[i], sum);
            temp = temp - cardPoints[k - (n - i)] + cardPoints[i];
        }
        return sum;

    }

    private static void chooseSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                count++;
                System.out.println("loop " + count + " round");
                if(arr[i]>arr[j]){
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{30, 23, 67, 89, 12, 2, 3, 65, 78, 111, 56, 45, 24, 32, 67, 12, 23, 7, 78, 89};
//        int[] array = new int[]{1, 79, 80, 1, 1, 1, 200, 1};
//        System.out.println(array.length);
//        sort(array);
        bubbleSort(array);
//        chooseSort(array);
        System.out.println(Arrays.toString(array));
//        System.out.println(maxScore(array, 3));
    }


}
