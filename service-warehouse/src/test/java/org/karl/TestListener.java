package org.karl;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author KARL ROSE
 * @date 2020/12/28 17:30
 **/
@Component
public class TestListener implements ApplicationListener<TestEvent> {

    @Override
    public void onApplicationEvent(TestEvent testEvent) {
        testEvent.print();
    }
}
