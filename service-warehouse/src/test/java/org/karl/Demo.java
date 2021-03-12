package org.karl;


/**
 * @author KARL ROSE
 * @date 2021/2/3 15:52
 **/
public class Demo {

    public static void main(String[] args) throws InterruptedException {

        /*Thread thread = new Thread();
        thread.start();
        System.out.println("======start======");
        System.out.println("isAlive:" + thread.isAlive());
        System.out.println("isInterrupted:" + thread.isInterrupted());
        thread.interrupt();
        System.out.println("======interrupt======");
        System.out.println("isAlive:" + thread.isAlive());
        System.out.println("isInterrupted:" + thread.isInterrupted());*/

//        threadTest();

        System.out.println(Integer.toBinaryString(12));


    }

    public static void threadTest() {
        new Thread() {
            @Override
            public void run() {
                int a = 100;
                try {
                    interrupt();
                    Thread.sleep(3000);
                    if (interrupted()) {
                        a = 300;
                        System.out.println(a);
                    }

                } catch (Exception e) {
                    a = 200;
                    System.out.println(a);
                } finally {
                    a = 500;
                    System.out.println(a);
                }
                System.out.println(a);
            }
        }.start();
    }

}
