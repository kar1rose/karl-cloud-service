package org.karl.base.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 异步阻塞demo
 *
 * @author karl.rose
 * @date 2020/4/27 18:45
 **/
public class CallableJoinDemo {


    private static final Logger logger = LoggerFactory.getLogger(CallableJoinDemo.class);

    private static final int SLEEP = 2000;

    static class HotWaterJob implements Callable<Boolean> {
        @Override
        public Boolean call() throws Exception {
            try {
                logger.info("洗好水壶");
                logger.info("灌上凉水");
                logger.info("放在火上");
                Thread.sleep(SLEEP * 5);
                logger.info("烧水结束");
            } catch (Exception e) {
                logger.error(e.getMessage());
                return false;
            }
            return true;
        }
    }

    static class WashJob implements Callable<Boolean> {
        @Override
        public Boolean call() throws Exception {
            try {
                logger.info("洗茶壶");
                logger.info("洗茶杯");
                logger.info("拿茶叶");
                Thread.sleep(SLEEP * 2);
                logger.info("洗水壶结束");
            } catch (Exception e) {
                logger.error(e.getMessage());
                return false;
            }
            return true;
        }
    }

    public static void drink(boolean hot, boolean cleanUp) {
        if (hot && cleanUp) {
            logger.info("泡茶喝");
        } else if (!hot) {
            logger.error("水还没好");
        } else {
            logger.error("杯子还没洗干净");
        }
    }

    public static void main(String[] args) {

        Callable<Boolean> hJob = new HotWaterJob();
        FutureTask<Boolean> hTask = new FutureTask<>(hJob);
        Thread hThread = new Thread(hTask, "烧水-Thread");

        Callable<Boolean> wJob = new WashJob();
        FutureTask<Boolean> wTask = new FutureTask<>(wJob);
        Thread wThread = new Thread(wTask, "清洗-Thread");

        hThread.start();
        wThread.start();

        Thread.currentThread().setName("泡茶主线程");
        try {
            //线程异步阻塞
            boolean hot = hTask.get();
            boolean water = wTask.get();
            drink(hot, water);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
        logger.info("喝茶结束");


    }
}
