package org.karl.zk.curator;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * zookeeper操作类
 *
 * @author karl.rose
 * @date 2020/6/2 16:05
 **/
@Slf4j
public class CustomZkClient {

    private static final RetryPolicy RETRY_POLICY = new ExponentialBackoffRetry(1000, 3);

    CuratorFramework client;

    private static final String ZK_URL = "127.0.0.1:2181";

    public CustomZkClient() {
        this.client = CuratorFrameworkFactory.newClient(ZK_URL, RETRY_POLICY);
        client.start();
        System.out.println("zookeeper connected");
    }

    public void destroy() {
        if (client != null) {
            CloseableUtils.closeQuietly(client);
            System.out.println("zookeeper connection closed");
        }
    }

    public static void main(String[] args) throws Exception {
        CustomZkClient zkClient = new CustomZkClient();
        for (int i = 0; i < 10; i++) {
            zkClient.createNode("/karl/test_");
        }
        List<String> list = zkClient.listNodes("/karl");
        for (String s : list) {
            System.out.println("node:" + s);
        }
        zkClient.destroy();
    }

    public void createNode(String node) {
        createNode(node, CreateMode.PERSISTENT_SEQUENTIAL);
    }

    public void createNode(String node, CreateMode mode) {
        try {
            client.create().creatingParentsIfNeeded().withMode(mode).forPath(node, node.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> listNodes(String node) {
        try {
            Stat stat = client.checkExists().forPath(node);
            if (stat != null) {
                return client.getChildren().forPath(node);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void update() {
        try {
            AsyncCallback.StringCallback callback = (i, s, o, s1) -> System.out.println(i + s + o + s1);
            String node = "/karl/data0";
            String data = "karl zookeeper data ";
            byte[] payload = data.getBytes();
            client.start();
            client.setData().inBackground(callback).forPath(node, payload);
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(String node) {
        try {
            client.delete().forPath(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
