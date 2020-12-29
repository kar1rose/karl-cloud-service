package org.karl;

import org.springframework.context.ApplicationEvent;

/**
 * @author KARL ROSE
 * @date 2020/12/21 18:12
 **/
public class TestEvent extends ApplicationEvent {

    private String msg;

    public TestEvent(Object source) {
        super(source);
    }

    public TestEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public void print() {
        System.out.println(msg);
    }
}
