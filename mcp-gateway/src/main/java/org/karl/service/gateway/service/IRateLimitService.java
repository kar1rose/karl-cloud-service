package org.karl.service.gateway.service;

/**
 * 路径限速器
 *
 * @author KARL.ROSE
 * @date 2021/4/1 16:53
 */
public interface IRateLimitService {

    /**
     * 对应的url是否被限速
     *
     * @param url 路径
     * @param limitCount 每秒限制数量
     * @return 是否通过
     * @author KARL.ROSE
     * @date 2021/4/1 16:53
     */
    Boolean limited(String url,Integer limitCount);
}
