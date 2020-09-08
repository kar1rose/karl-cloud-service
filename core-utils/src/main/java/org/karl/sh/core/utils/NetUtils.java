package org.karl.sh.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

/**
 * @author KARL ROSE
 * @date 2020/5/21 12:02
 **/
@Slf4j
public class NetUtils {

    private static final int MIN = 8100;
    private static final int MAX = 10000;
    public static final String LOCAL_HOST = "127.0.0.1";

    /***
     * 测试主机Host的port端口是否被使用
     * @param host 主机
     * @param port 端口
     */
    public static boolean portOccupied(String host, int port) {
        boolean flag = false;
        Socket socket = null;
        try {
            InetAddress address = InetAddress.getByName(host);
            socket = new Socket(address, port);
            flag = true;
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    public static int achieveAvailablePort() {
        Random random = new Random();
        int port = random.nextInt(MAX) % (MAX - MIN + 1) + MIN;
        boolean using = NetUtils.portOccupied(LOCAL_HOST, port);
        if (using) {
            return achieveAvailablePort();
        } else {
            return port;
        }
    }

    public static void start(String[] args) {
        boolean isServerPort = false;
        String serverPort = "";
        if (args != null) {
            for (String arg : args) {
                if (StringUtils.hasText(arg) && arg.startsWith("--server.port")) {
                    isServerPort = true;
                    serverPort = arg;
                    break;
                }
            }
        }
        if (!isServerPort) {
            //没有指定端口，则随机生成一个可用的端口
            int port = achieveAvailablePort();
            log.info("current server.port=" + port);
            System.setProperty("server.port", String.valueOf(port));
        } else {//指定了端口，则以指定的端口为准
            log.info("current server.port=" + serverPort.split("=")[1]);
            System.setProperty("server.port", serverPort.split("=")[1]);
        }
    }


}
