package org.karl.parent;
/**
 * Created by KARL_ROSE on 2021/2/4 19:21
 */

/**
 * @ClassName ServiceA
 * @Author ROSE
 * @Date 2021/2/4 19:21
 * @Description
 **/
public class ServiceA {

    static {
        System.out.println("staticA");
    }

    /*public ServiceA(){
        System.out.println("A");
    }*/

    public static void main(String[] args) {
        ServiceA service = new ServiceB();
        service = new ServiceB();
    }
}
