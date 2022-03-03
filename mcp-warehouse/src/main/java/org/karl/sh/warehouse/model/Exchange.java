package org.karl.sh.warehouse.model;

/**
 * 1
 *
 * @author Lawrence.Luo
 * @date 2022/2/25 13:28
 */
public class Exchange {

    String ex = "good";
    char[]c = {'a','b','c'};

    public static void main(String[] args) {
        int a = 0,c=0;
        do {
            c--;
            a=a-1;
        }while (a>0);

        System.out.println(c);
    }

    public void change(String str,char[]ch){
        str = "test";
        ch[0] = '1';
    }
}
