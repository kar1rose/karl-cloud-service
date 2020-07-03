package org.karl;

/**
 * @author KARL ROSE
 * @date 2020/7/2 20:38
 **/
public class GcTest {

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize调用");
    }

    public static void main(String[] args) {
        GcTest test = new GcTest();
        test = null;
//        System.gc();
    }
}
