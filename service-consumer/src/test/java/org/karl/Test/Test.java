package org.karl.Test;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author KARL ROSE
 * @date 2020/8/19 19:05
 **/
public class Test {

    private static final int HTTP_CONN_TIME_OUT = 10000;

    /**
     * 请求配置
     **/
    private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom().
            setConnectionRequestTimeout(HTTP_CONN_TIME_OUT)
            .setConnectTimeout(HTTP_CONN_TIME_OUT)
            .setSocketTimeout(HTTP_CONN_TIME_OUT)
            .build();


    public static void main(String[] args) throws InterruptedException {
        int size = 100;
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(size);
        AtomicInteger atomicInteger = new AtomicInteger();
        for (int i = 0; i < size; i++) {
            executorService.execute(() -> {
                CloseableHttpClient http = HttpClients.createDefault();
                HttpPost post = new HttpPost("http://127.0.0.1:8001/order/918/100002/1");
                post.setConfig(REQUEST_CONFIG);
                try (CloseableHttpResponse httpResponse = http.execute(post)) {
                    HttpEntity httpEntity = httpResponse.getEntity();
                    if (httpEntity != null) {
                        String result = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
                        System.out.println("get post result : " + result);
                    }
                    atomicInteger.incrementAndGet();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await(10, TimeUnit.SECONDS);
        //线程暂停等十秒
//        executorService.awaitTermination(10, TimeUnit.SECONDS);
        if (atomicInteger.get() != size) {
            System.out.println("执行完成数:" + atomicInteger.get());
        }
        System.out.println("执行完成");
        executorService.shutdown();
        long end = System.currentTimeMillis();
        System.out.println("over in " + (end - start) / 1000 + "seconds");

    }



}
