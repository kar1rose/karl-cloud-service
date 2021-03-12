package org.karl.base.async;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author KARL ROSE
 * @date 2020/8/20 16:54
 **/
@Slf4j
public class KarlJob implements Runnable {

    @SneakyThrows
    @Override
    public void run() {
        new KarlThread().start();
        int i = 0;
        while (i < 15) {
            log.info("干得漂亮");
            Thread.sleep(1000);
            i++;
        }
    }

    public static void main(String[] args) {
        new KarlJob().run();
    }
}

class KarlThread extends Thread{
    @Override
    public void run() {
        super.run();
        System.out.println("thread start");
    }
}
