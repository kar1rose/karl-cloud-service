package org.karl.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author KARL ROSE
 * @date 2020/12/28 17:30
 **/
@Component
@Slf4j
public class Listener implements ApplicationListener<RegisterEvent> {

    @Async
    @Override
    public void onApplicationEvent(RegisterEvent event) {
        log.info("监听到消息:" + event.getMessage());
    }
}
