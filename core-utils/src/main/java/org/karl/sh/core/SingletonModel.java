package org.karl.sh.core;

/**
 * @author KARL ROSE
 * @date 2021/2/3 14:37
 **/
public class SingletonModel {

    private static SingletonModel instance;

    public synchronized static void init() {
        if (instance == null) {
            instance = new SingletonModel();
        }
    }

    public SingletonModel() {

    }

    public static SingletonModel getInstance() {
        if (instance == null) {
            init();
        }
        return instance;
    }

    public static void main(String[] args) {
        SingletonModel mode = SingletonModel.getInstance();
        SingletonModel mode1 = SingletonModel.getInstance();
        System.out.println(mode == mode1);

    }

}
