package org.karl;

/**
 * @author KARL ROSE
 * @date 2020/11/10 14:42
 **/
public class Bar {

    int a = 1;
    static int b = 2;

    public int sum(int c) {
        return a + b + c;
    }

    public static void main(String[] args) {
        new Bar().sum(5);
    }
}
