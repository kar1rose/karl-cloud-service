package org.karl.im.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.karl.im.ServerConstants;
import org.karl.zk.curator.CustomZkClient;

/**
 * @author KARL ROSE
 * @date 2020/6/11 14:38
 **/
@Slf4j
public class ImWorker {

    private CuratorFramework client = null;

    private String pathRegistered = null;

    private ImNode node = null;

    private static ImWorker singleInstance = null;

    public static ImWorker instance() {
        if (null == singleInstance) {
            singleInstance = new ImWorker();
            CustomZkClient.init();
            singleInstance.client = CustomZkClient.instance.getClient();
        }

        return singleInstance;
    }

    private ImWorker() {

    }

    public void init() {
        CustomZkClient.instance.createNode(ServerConstants.ROOT, null);
        try {
            byte[] payload = JSON.toJSONBytes(node, SerializerFeature.QuoteFieldNames);
            pathRegistered = client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(ServerConstants.PREFIX, payload);
            node.setId(getId());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public long getId() {
        String sid = null;
        if (null == pathRegistered) {
            log.error("节点注册失败");
            throw new RuntimeException();
        }
        int index = pathRegistered.lastIndexOf(ServerConstants.PREFIX);
        if (index >= 0) {
            index += ServerConstants.PREFIX.length();
            sid = index <= pathRegistered.length() ? pathRegistered.substring(index) : null;
        }
        if (null == sid) {
            log.error("节点生成失败");
            throw new RuntimeException();
        }
        return Long.parseLong(sid);
    }

    public boolean incBalance() {
        if (null == node) {
            log.error("未设置节点");
            throw new RuntimeException();
        }
        while (true) {
            try {
                node.getBalance().getAndIncrement();
                byte[] payload = JSON.toJSONBytes(node, SerializerFeature.QuoteFieldNames);
                client.setData().forPath(pathRegistered, payload);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    public boolean decrBalance(){

        if (null == node) {
            log.error("未设置节点");
            throw new RuntimeException();
        }
        while (true) {
            try {
                int i = node.getBalance().decrementAndGet();
                if(i<0){
                    node.getBalance().set(0);
                }
                byte[] payload = JSON.toJSONBytes(node, SerializerFeature.QuoteFieldNames);
                client.setData().forPath(pathRegistered, payload);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }
}
