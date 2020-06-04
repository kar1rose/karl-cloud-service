package org.karl.zk.curator;

/**
 * 测试类
 *
 * @author karl.rose
 * @date 2020/6/2 17:36
 **/
public class Test {

    public static void main(String[] args) {
        System.out.println(sum(8));
    }

    public static int sum(int n){
        if(n==1){
            return 1;
        }
        return n + sum(n-1);
    }
}
