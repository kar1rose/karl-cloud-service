package org.karl.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author KARL ROSE
 * @date 2021/2/3 15:02
 **/
@Component
public class Publisher {

    @Autowired
    private ApplicationContext applicationContext;

    public void publish(String message){
        //发布事件
        applicationContext.publishEvent(new RegisterEvent(this, message));
    }

}
