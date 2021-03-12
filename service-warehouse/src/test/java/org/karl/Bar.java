package org.karl;

import java.util.UUID;

/**
 * @author KARL ROSE
 * @date 2020/11/10 14:42
 **/
public class Bar {


    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(UUID.randomUUID().toString().replace("-", ""));
        }
    }
}
