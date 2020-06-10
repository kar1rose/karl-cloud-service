package org.karl.zk.curator;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author KARL ROSE
 * @date 2020/6/5 16:39
 **/
public class Polling {

    private static Polling instance = null;
    private BlockingDeque<Integer> blockingDeque = null;

    private Polling() {
        blockingDeque = new LinkedBlockingDeque<Integer>() {
            {
                add(10001);
                add(10002);
                add(10003);
                add(10004);
                add(10005);
            }
        };
    }

    public static Polling getInstance() {
        if (instance == null) {
            instance = new Polling();
        }
        return instance;
    }


    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Polling polling = Polling.getInstance();
        for (int i = 0; i < 10; i++) {
            Integer userId = polling.blockingDeque.take();
            polling.blockingDeque.add(userId);
            System.out.println("为" + userId + "分配任务");
        }

        Integer nowId = 0;
        while (nowId != 10003) {
            nowId = polling.blockingDeque.take();
            polling.blockingDeque.add(nowId);
            System.out.println("跳过用户:" + nowId);
        }

        for (int i = 0; i < 10; i++) {
            Integer userId = polling.blockingDeque.take();
            polling.blockingDeque.add(userId);
            System.out.println("为" + userId + "分配任务");
        }
        long end = System.currentTimeMillis();
        System.out.println("used:" + (end - start));
    }

}

