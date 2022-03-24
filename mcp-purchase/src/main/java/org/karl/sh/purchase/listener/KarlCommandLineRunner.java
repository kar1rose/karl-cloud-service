package org.karl.sh.purchase.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 异步监听
 *
 * @author KARL.ROSE
 * @date 2021/4/1 15:26
 */
@Component
public class KarlCommandLineRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(KarlCommandLineRunner.class);

    @Override
    public void run(String... args) throws Exception {
        logger.info("KarlCommandLineRunner run now");
    }
}
