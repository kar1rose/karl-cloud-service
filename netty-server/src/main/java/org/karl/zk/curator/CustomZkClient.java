package org.karl.zk.curator;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

/**
 * zookeeper操作,监控管理
 *
 * @author karl.rose
 * @date 2020/6/2 16:05
 **/
@Slf4j
public class CustomZkClient {

    private static final RetryPolicy RETRY_POLICY = new ExponentialBackoffRetry(1000, 3);
    private final CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_URL, RETRY_POLICY);
    public static CustomZkClient instance = null;
    private static final String ROOT = "/karl";
    private static final String SUB_NODE = "/karl/node_";
    private static final String ZK_URL = "139.224.83.117:2181";

    public static CustomZkClient init() {
        if (null == instance) {
            instance = new CustomZkClient();
            instance.client.start();
            log.info("zookeeper connected");
        }
        return instance;
    }

    public CuratorFramework getClient() {
        return instance.client;
    }

    private CustomZkClient() {
    }

    public void destroy() {
        if (client != null) {
            CloseableUtils.closeQuietly(client);
            log.info("zookeeper connection closed");
        }
    }

    public static void main(String[] args) throws Exception {
        CustomZkClient zkClient = CustomZkClient.init();
        try {
            zkClient.treeCache(ROOT);
//            zkClient.nodeCache(ROOT);
//            zkClient.childListener(ROOT);
            for (int i = 0; i < 10; i++) {
                log.info(zkClient.createNode(SUB_NODE, UUID.randomUUID().toString().getBytes()));
            }
            zkClient.update("/karl", "my data ".getBytes());

            List<String> list = zkClient.listNodes("/karl");
            list.forEach(path -> {
                zkClient.delete("/karl/" + path);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        zkClient.destroy();
    }

    public String createNode(String node, byte[] bytes) {
        return createNode(node, CreateMode.PERSISTENT_SEQUENTIAL, bytes);
    }

    public String createNode(String node, CreateMode mode, byte[] bytes) {
        try {
            return client.create().creatingParentsIfNeeded().withMode(mode).forPath(node, bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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


    public void update(String node, byte[] bytes) {
        try {
            AsyncCallback.StringCallback callback = (i, s, o, s1) -> log.info(i + s + o + s1);
            client.setData().inBackground(callback).forPath(node, bytes);
            Thread.sleep(2000);
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

    /**
     * 监听一次
     *
     * @author KARL.ROSE
     * @date 2020/6/9 5:16 下午
     **/
    public void watch() throws Exception {
        Watcher w = watchedEvent -> log.info("监听到变化:" + watchedEvent);
        byte[] data = client.getData().usingWatcher(w).forPath(ROOT);
        log.info("监听节点内容" + new String(data));
        for (int i = 0; i < 5; i++) {
            client.setData().forPath(ROOT, ("第" + i + "次变更内容").getBytes());
        }
        Thread.sleep(10000);
    }

    /**
     * 节点监听
     *
     * @author KARL.ROSE
     * @date 2020/6/9 5:16 下午
     **/
    public void nodeCache(String node) throws Exception {
        NodeCache nodeCache = new NodeCache(client, node, false);
        NodeCacheListener listener = () -> {
            ChildData childData = nodeCache.getCurrentData();
            log.info("node 节点状态改变，path = " + childData.getPath());
            log.info("node 节点状态改变，data = " + new String(childData.getData()));
            log.info("node 节点状态改变，stat = " + childData.getStat());
        };
        nodeCache.getListenable().addListener(listener);
        nodeCache.start();
    }

    public void childListener(String node) throws Exception {
        PathChildrenCache cache = new PathChildrenCache(client, node, true);
        PathChildrenCacheListener listener = (curatorFramework, pathChildrenCacheEvent) -> {
            ChildData data = pathChildrenCacheEvent.getData();
            switch (pathChildrenCacheEvent.getType()) {
                case CHILD_ADDED:
                    log.info("子节点增加:" + data.getPath() + " data=" + new String(data.getData(), StandardCharsets.UTF_8));
                    break;
                case CHILD_UPDATED:
                    log.info("子节点更新:" + data.getPath() + " data=" + new String(data.getData(), StandardCharsets.UTF_8));
                    break;
                case CHILD_REMOVED:
                    log.info("子节点删除:" + data.getPath() + " data=" + new String(data.getData(), StandardCharsets.UTF_8));
                    break;
                default:
                    break;
            }
        };
        cache.getListenable().addListener(listener);
        cache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
    }


    public void treeCache(String node) throws Exception {
        TreeCache cache = new TreeCache(client, node);
        TreeCacheListener listener = (curatorFramework, treeCacheEvent) -> {
            ChildData data = treeCacheEvent.getData();
            switch (treeCacheEvent.getType()) {
                case NODE_ADDED:
                    log.info("treeCache子节点增加:" + data.getPath() + " data=" + new String(data.getData(), StandardCharsets.UTF_8));
                    break;
                case NODE_UPDATED:
                    log.info("treeCache子节点更新:" + data.getPath() + " data=" + new String(data.getData(), StandardCharsets.UTF_8));
                    break;
                case NODE_REMOVED:
                    log.info("treeCache子节点删除:" + data.getPath() + " data=" + new String(data.getData(), StandardCharsets.UTF_8));
                    break;
                default:
                    break;
            }
        };
        cache.getListenable().addListener(listener);
        cache.start();

    }

}


