package org.karl.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author KARL ROSE
 * @date 2020/12/21 18:12
 **/
public class RegisterEvent extends ApplicationEvent {

    private String msg;

    public RegisterEvent(Object source) {
        super(source);
    }

    public RegisterEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMessage() {
        return msg;
    }

}
