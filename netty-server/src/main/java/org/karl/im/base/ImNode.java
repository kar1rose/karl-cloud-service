package org.karl.im.base;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author KARL ROSE
 * @date 2020/6/11 14:33
 **/
@Data
public class ImNode {

    /**
     * work id zk 生成
     **/
    private long id;
    /**
     * netty 服务连接数
     **/
    private AtomicInteger balance;
    /**
     * netty 服务ip
     **/
    private String host;
    /**
     * netty 服务端口
     **/
    private String port;

    public ImNode(String host, String port) {
        this.host = host;
        this.port = port;
    }

    /*public int compareTo(ImNode o) {
        int weight1 = this.getBalance().get();
        int weight2 = o.getBalance().get();
        if (weight1 > weight2) {
            return 1;
        } else if (weight1 < weight2) {
            return -1;
        }
        return 0;
    }*/
}
