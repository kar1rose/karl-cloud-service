package org.karl.base.async;

import com.google.common.util.concurrent.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @author KARL ROSE
 * @date 2020/4/28 11:09
 **/
public class GuavaDemo {


    private static final Logger logger = LoggerFactory.getLogger(GuavaDemo.class);

    private static final Integer CORE_POOL_SIZE = 10;
    private static final Integer MAX_POOL_SIZE = 20;
    private static final Long KEEP_ALIVE_TIME = 200L;
    private static final int SLEEP = 2000;

    private static final Logger log = LoggerFactory.getLogger("GuavaDemo");

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
        //线程监听器
        ListeningExecutorService pool = MoreExecutors.listeningDecorator(executorService);
        CountDownLatch latch = new CountDownLatch(2);

        MainJob job = new MainJob();
        Thread mainThread = new Thread(job);
        mainThread.setName("主线程");
        mainThread.start();
        logger.info("主线程启动");

        logger.info("开始烧水了");
        Callable<Boolean> hJob = new CallableJoinDemo.HotWaterJob();
        ListenableFuture<Boolean> hot = pool.submit(hJob);
        Futures.addCallback(hot, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                if (aBoolean) {
                    job.hot = true;
                }
                latch.countDown();
            }
            @Override
            public void onFailure(Throwable throwable) {
                logger.error("烧水失败了");
                mainThread.interrupt();
                latch.countDown();
            }
        }, executorService);

        logger.info("开始洗水壶了");
        Callable<Boolean> wJob = new CallableJoinDemo.WashJob();
        ListenableFuture<Boolean> wash = pool.submit(wJob);
        Futures.addCallback(wash, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                if (aBoolean) {
                    job.wash = true;
                }
                latch.countDown();
            }
            @Override
            public void onFailure(Throwable throwable) {
                logger.error("杯子摔碎了");
                mainThread.interrupt();
                latch.countDown();
            }
        }, executorService);

        logger.info("把书一摔准备喝茶");
        try {
            latch.await(10,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(mainThread.isInterrupted()){
            logger.error("喝个毛茶");
        }
        logger.info("喝茶喝茶");

        executorService.shutdown();

    }
}
