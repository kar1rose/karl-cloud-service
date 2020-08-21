package org.karl.base.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异步阻塞demo
 *
 * @author karl.rose
 * @date 2020/4/27 18:45
 **/
public class JoinDemo {


    private static final Logger log = LoggerFactory.getLogger("JoinDemo");
    private static final int SLEEP = 2000;

    static class HotWaterThread extends Thread {
        public HotWaterThread() {
            super("烧水thread");
        }

        @Override
        public void run() {
            try {
                log.info("洗好水壶");
                log.info("灌上凉水");
                log.info("放在火上");
                Thread.sleep(SLEEP * 5);
                log.info("烧水结束");
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    static class WashThread extends Thread {
        public WashThread() {
            super("清洗thread");
        }

        @Override
        public void run() {
            try {
                log.info("洗茶壶");
                log.info("洗茶杯");
                log.info("拿茶叶");
                Thread.sleep(SLEEP);
                log.info("洗水壶结束");
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Thread.currentThread().setName("主线程");
        Thread washThread = new WashThread();
        Thread hotThread = new HotWaterThread();
        washThread.start();
        hotThread.start();
        try {
            //thread同步阻塞
            washThread.join();
            log.info("等水烧开");
            hotThread.join();
            log.info("泡茶喝");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("泡茶结束");

    }
}
