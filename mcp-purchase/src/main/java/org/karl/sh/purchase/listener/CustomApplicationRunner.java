package org.karl.sh.purchase.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * application runner
 *
 * @author Lawrence.Luo
 * @date 2022/3/25 18:01
 */
@Component
@Slf4j
public class CustomApplicationRunner implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("CustomApplicationRunner run now");
    }
}
