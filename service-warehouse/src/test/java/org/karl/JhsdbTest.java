package org.karl;

/**
 * @author KARL ROSE
 * @date 2020/10/21 16:48
 **/
public class JhsdbTest {

    static class Test {
        static ObjectHolder staticObj = new ObjectHolder();
        ObjectHolder instanceObj = new ObjectHolder();

        void foo() {
            ObjectHolder localObj = new ObjectHolder();
            System.out.println("done....");
        }
    }

    private static class ObjectHolder {

    }

    public static void main(String[] args) {
        /*Test test = new JhsdbTest.Test();
        test.foo();*/
        System.out.println(Integer.toBinaryString(-3));
        for (char s : "hello".toCharArray()) {
            System.out.println("0" + Integer.toBinaryString(s));
        }
    }
}
