package org.karl.sh.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author KARL.ROSE
 * @date 2020/4/22 14:31
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties
@EnableCaching
@EnableTransactionManagement
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

}
