package org.karl.zk.lock;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.karl.zk.curator.CustomZkClient;

import java.util.concurrent.*;

/**
 * @author KARL ROSE
 * @date 2020/6/10 15:27
 **/
@Slf4j
public class ZkLockTest {

    private static final Integer CORE_POOL_SIZE = 10;
    private static final Integer MAX_POOL_SIZE = 20;
    private static final Long KEEP_ALIVE_TIME = 200L;
    private static final int CONCURRENT_COUNT = 10;
    private static final int COUNT = 10;

    int count = 0;


    public void schedule() throws InterruptedException {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), threadFactory);
        for (int i = 0; i < CONCURRENT_COUNT; i++) {
            executorService.execute(() -> {
                log.info("线程启动");
                ZkLock zkLock = new ZkLockImpl();
                zkLock.lock();
                for (int j = 0; j < COUNT; j++) {
                    count++;
                }
                log.info("count=" + count);
                zkLock.release();
                log.info("线程结束");
            });
        }
        Thread.sleep(10000);
        executorService.shutdown();
    }

    /**
     * zk 分布式可重入锁
     *
     * @author KARL.ROSE
     * @date 2020/6/10 5:34 下午
     **/
    public void zkMutex() throws InterruptedException {
        CustomZkClient.init();
        CuratorFramework client = CustomZkClient.instance.getClient();

        final InterProcessMutex mutex = new InterProcessMutex(client, "/mutex");

        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), threadFactory);
        for (int i = 0; i < CONCURRENT_COUNT; i++) {
            executorService.execute(() -> {
                log.info("线程启动");
                try {
                    mutex.acquire();
                    for (int j = 0; j < COUNT; j++) {
                        count++;
                    }
                    log.info("count=" + count);
                    mutex.release();
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
                log.info("线程结束");
            });
        }
        Thread.sleep(10000);
        executorService.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        new ZkLockTest().schedule();
//        new ZkLockTest().zkMutex();
    }
}
