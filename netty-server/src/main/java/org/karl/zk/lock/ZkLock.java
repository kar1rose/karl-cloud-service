package org.karl.zk.lock;

/**
 * @author KARL ROSE
 * @date 2020/6/10 14:41
 **/
public interface ZkLock {

    /**
     * 加锁是否成功
     *
     * @return boolean
     * @author KARL.ROSE
     * @date 2020/6/10 2:41 下午
     **/
    boolean lock();

    /**
     * 解锁是否成功
     *
     * @return boolean
     * @author KARL.ROSE
     * @date 2020/6/10 2:41 下午
     **/
    boolean release();
}
