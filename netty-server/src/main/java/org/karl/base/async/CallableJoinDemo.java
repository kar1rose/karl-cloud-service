package org.karl.base.async;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 异步阻塞demo
 *
 * @author karl.rose
 * @date 2020/4/27 18:45
 **/
@Slf4j(topic = "Callable Demo")
public class CallableJoinDemo {

    private static final int SLEEP = 2000;

    static class HotWaterJob implements Callable<Boolean> {
        @Override
        public Boolean call() throws Exception {
            try {
                log.info("洗好水壶");
                log.info("灌上凉水");
                log.info("放在火上");
                Thread.sleep(SLEEP * 5);
                log.info("烧水结束");
            } catch (Exception e) {
                log.error(e.getMessage());
                return false;
            }
            return true;
        }
    }

    static class WashJob implements Callable<Boolean> {
        @Override
        public Boolean call() throws Exception {
            try {
                log.info("洗茶壶");
                log.info("洗茶杯");
                log.info("拿茶叶");
                Thread.sleep(SLEEP * 2);
                log.info("洗水壶结束");
            } catch (Exception e) {
                log.error(e.getMessage());
                return false;
            }
            return true;
        }
    }

    public static void drink(boolean hot, boolean cleanUp) {
        if (hot && cleanUp) {
            log.info("泡茶喝");
        } else if (!hot) {
            log.error("水还没好");
        } else {
            log.error("杯子还没洗干净");
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
            log.error(e.getMessage());
        }
        log.info("喝茶结束");


    }
}
