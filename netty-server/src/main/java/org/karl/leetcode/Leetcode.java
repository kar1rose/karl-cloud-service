package org.karl.leetcode;

import java.util.Arrays;

/**
 * @author KARL ROSE
 * @date 2020/6/8 16:21
 **/
public class Leetcode {

    public static void main(String[] args) {

//        String[] arr = new String[]{"c==c", "f!=a", "f==b", "b==c"};
//        System.out.println(new Leetcode().equationsPossible(arr));
        System.out.println(new Leetcode().translateNum(1225));

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
                System.out.println(Arrays.toString(parent));
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

}
