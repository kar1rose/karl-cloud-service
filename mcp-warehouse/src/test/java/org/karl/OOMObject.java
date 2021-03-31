package org.karl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author KARL ROSE
 * @date 2020/10/27 15:07
 **/
public class OOMObject {

    static class OomObj {
        public byte[] placeHolder = new byte[64 * 1024];
    }

    public static void fillHeap() throws InterruptedException {
        List<OomObj> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Thread.sleep(50);
            System.out.println(i);
            list.add(new OomObj());
        }
    }

    public static void createBusyThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                }
            }
        }, "testBusyThread");
        thread.start();
    }

    public static void createLockThread(final Object lock) {
        Thread thread = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "testLockThread");
        thread.start();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
//        fillHeap();
//        System.gc();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        bufferedReader.readLine();
        createBusyThread();
        bufferedReader.readLine();
        Object object = new Object();
        createLockThread(object);
    }
}
