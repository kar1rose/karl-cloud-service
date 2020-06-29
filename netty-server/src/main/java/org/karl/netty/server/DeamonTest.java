package org.karl.netty.server;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author KARL ROSE
 * @date 2020/6/11 15:56
 **/
@Slf4j
public class DeamonTest {

    public static void main(String[] args) throws InterruptedException {
        long start = System.nanoTime();
        Thread a = new Thread(() -> {
            try {
                TimeUnit.DAYS.sleep(Long.MAX_VALUE);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }, "Deamon*T");
        a.setDaemon(true);
        a.start();
        TimeUnit.SECONDS.sleep(5);
        log.info("系统退出,执行{}秒", (System.nanoTime() - start)/1000/1000/1000);
    }
}
