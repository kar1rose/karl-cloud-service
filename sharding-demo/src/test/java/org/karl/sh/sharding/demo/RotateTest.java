package org.karl.sh.sharding.demo;

/**
 * 测试
 *
 * @author Lawrence.Luo
 * @date 2022/1/30 10:48
 */
public class RotateTest {
    public static boolean checkInclusion(String s1, String s2) {
        int len = s2.length();
        int len1 = s1.length();
        for (int i = 0; i < len - len1 + 1; i++) {
            if (s2.charAt(i) == s1.charAt(0)) {
                int j = 1;
                while (j < s1.length() && s1.charAt(j) == s2.charAt(i + j)) {
                    j++;
                }
                if (j == s1.length()) {
                    return true;
                }
            }
        }
        for (int i = 0; i < len; i++) {
            if (s2.charAt(i) == s1.charAt(0)) {
                int j = 1;
                while (j < s1.length() && s1.charAt(j) == s2.charAt(i - j)) {
                    j++;
                }
                if (j == s1.length()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.print(checkInclusion("a", "bca"));
    }

    public void quickSort(int[] arr, int start, int end) {
        int mid = arr[start];
        int i=start,j=end;
        while (i<j){
            while (i<j&&arr[i]<mid){
                i++;
            }
            while(i<j&&arr[j]>mid){
                j--;
            }
        }
    }
}
