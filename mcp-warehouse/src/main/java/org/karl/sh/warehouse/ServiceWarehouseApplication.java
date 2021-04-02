package org.karl.sh.warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

/**
 * @author KARL.ROSE
 * @date 2020/4/22 14:31
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties
@EnableCaching
@EnableTransactionManagement
public class ServiceWarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceWarehouseApplication.class, args);
    }


    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
