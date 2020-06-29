package org.karl.zk.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.karl.zk.curator.CustomZkClient;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author KARL ROSE
 * @date 2020/6/10 14:43
 **/
@Slf4j
public class ZkLockImpl implements ZkLock {

    private static final String ROOT = "/lock";
    private static final String NODE = ROOT + "/seq";
    private static final long WAIT_TIME = 1000;

    private final CuratorFramework client;

    private String priorPath = null;
    private String lockedShortPath = null;
    private String lockedPath = null;
    final AtomicInteger lockCount = new AtomicInteger(0);
    private Thread thread;

    public ZkLockImpl() {
        CustomZkClient.init();
        try {
            if (null == CustomZkClient.instance.getClient().checkExists().forPath(ROOT)) {
                CustomZkClient.instance.createNode(ROOT, null);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        client = CustomZkClient.instance.getClient();
    }

    @Override
    public boolean lock() {
        synchronized (this) {
            log.info("LOCK_COUNT={}", lockCount);
            if (lockCount.get() == 0) {
                thread = Thread.currentThread();
                lockCount.incrementAndGet();
            } else {
                if (!thread.equals(Thread.currentThread())) {
                    //不是当前线程 获取锁失败
                    return false;
                }
                lockCount.incrementAndGet();
                return true;
            }
            try {
                boolean locked;
                locked = tryLock();
                if (locked) {
                    return true;
                }
                while (!locked) {
                    waits();
                    List<String> waits = getWaits();
                    if (checkLocked(waits)) {
                        locked = true;
                    }
                }
                return true;
            } catch (Exception e) {
                log.error(e.getMessage());
                release();
            }
            return false;
        }
    }

    @Override
    public boolean release() {
        log.info("解锁:{}", lockedPath);
        if (!thread.equals(Thread.currentThread())) {
            return false;
        }
        int newLockedCount = lockCount.decrementAndGet();

        if (newLockedCount < 0) {
            throw new IllegalMonitorStateException("计数异常" + lockedPath);
        }
        if (newLockedCount != 0) {
            return true;
        }
        try {
            if (null != client.checkExists().forPath(lockedPath)) {
                client.delete().forPath(lockedPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean tryLock() throws Exception {
        lockedPath = CustomZkClient.init().createNode(NODE, CreateMode.EPHEMERAL_SEQUENTIAL, null);
        if (null == lockedPath) {
            throw new Exception("zk 加锁失败");
        }
        lockedShortPath = getShortPath(lockedPath);
        List<String> wait = getWaits();
        if (checkLocked(wait)) {
            return true;
        }
        int index = Collections.binarySearch(wait, lockedShortPath);
        if (index < 0) {
            throw new Exception("节点查询失败" + lockedShortPath);
        }
        priorPath = ROOT + "/" + wait.get(index - 1);
        return false;
    }

    private boolean checkLocked(List<String> list) {
        Collections.sort(list);
        if (lockedShortPath.equals(list.get(0))) {
            log.info("成功获取分布式节点锁:" + lockedShortPath);
            return true;
        }
        return false;
    }

    private String getShortPath(String path) {
        int index = path.lastIndexOf(ROOT);
        if (index >= 0) {
            index = ROOT.length() + 1;
            return index <= path.length() ? path.substring(index) : "";
        }
        return null;
    }

    private void waits() throws Exception {
        log.info("为前一节点添加监听:{}", priorPath);
        if (null == priorPath) {
            throw new Exception("priorPath error");
        }
        final CountDownLatch latch = new CountDownLatch(1);
        Watcher watcher = watchedEvent -> {
            log.info("监听到变化" + watchedEvent);
            log.info("{}节点删除", watchedEvent.getPath());
            latch.countDown();
        };
        client.getData().usingWatcher(watcher).forPath(priorPath);
        latch.await(WAIT_TIME, TimeUnit.SECONDS);
    }


    private List<String> getWaits() {
        List<String> children;
        try {
            //获取/ROOT节点下的字节点
            children = client.getChildren().forPath(ROOT);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return children;
    }
}
