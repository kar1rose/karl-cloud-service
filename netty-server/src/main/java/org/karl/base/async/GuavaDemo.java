package org.karl.base.async;

import com.google.common.util.concurrent.*;
import com.sun.istack.internal.Nullable;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author KARL ROSE
 * @date 2020/4/28 11:09
 **/
@Slf4j
public class GuavaDemo {

    private static final Integer CORE_POOL_SIZE = 10;
    private static final Integer MAX_POOL_SIZE = 20;
    private static final Long KEEP_ALIVE_TIME = 200L;
    private static final int SLEEP = 2000;

    static class MainJob implements Runnable {
        boolean wash = false;
        boolean hot = false;

        @Override
        public void run() {
            while (!wash || !hot) {
                try {
                    Thread.sleep(SLEEP);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                CallableJoinDemo.drink(wash, hot);
            }
        }
    }

    public static void main(String[] args) {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), threadFactory);
        ListeningExecutorService pool = MoreExecutors.listeningDecorator(executorService);

        MainJob job = new MainJob();
        Thread mainThread = new Thread(job);
        mainThread.setName("主线程");
        mainThread.start();
        log.info("主线程启动");

        log.info("开始烧水了");
        Callable<Boolean> hJob = new CallableJoinDemo.HotWaterJob();
        ListenableFuture<Boolean> hot = pool.submit(hJob);
        Futures.addCallback(hot, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    job.hot = true;
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                log.error("烧水失败了");
            }
        });

        log.info("开始洗水壶了");
        Callable<Boolean> wJob = new CallableJoinDemo.WashJob();
        ListenableFuture<Boolean> wash = pool.submit(wJob);
        Futures.addCallback(wash, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    job.wash = true;
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                log.error("杯子摔碎了");
            }
        });

        log.info("把书一摔准备喝茶");

    }
}
