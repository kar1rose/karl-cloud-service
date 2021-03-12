package org.karl.parent;
/**
 * Created by KARL_ROSE on 2021/2/4 19:47
 */

import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

import java.util.concurrent.*;

/**
 * @ClassName SyncTest
 * @Author ROSE
 * @Date 2021/2/4 19:47
 * @Description
 **/
public class SyncTest {

    public synchronized void test() {
        System.out.println("同步方法1:" + Thread.currentThread().getName());
       /* try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    public synchronized void test2() {
        System.out.println("同步方法2:" + Thread.currentThread().getName());
        /*try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    public static void main(String[] args) throws InterruptedException {
        SyncTest test = new SyncTest();
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();
        CountDownLatch latch = new CountDownLatch(2);
        ExecutorService service = new ThreadPoolExecutor(5, 10, 2000,
                TimeUnit.MILLISECONDS, workQueue);
        for (int i = 0; i < 2; i++) {
            service.execute(() -> {
                try {
                    test.test();
                    test.test2();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await(10,TimeUnit.SECONDS);

        service.shutdown();
    }
}
