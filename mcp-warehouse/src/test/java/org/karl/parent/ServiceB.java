package org.karl.parent;
/**
 * Created by KARL_ROSE on 2021/2/4 19:21
 */

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * @ClassName ServiceB
 * @Author ROSE
 * @Date 2021/2/4 19:21
 * @Description
 **/
public class ServiceB extends ServiceA{

    static {
        System.out.println("staticB");
    }

    public ServiceB(){
        System.out.println("B");
    }
}
