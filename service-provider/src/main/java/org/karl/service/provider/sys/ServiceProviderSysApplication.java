package org.karl.service.provider.sys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author KARL.ROSE
 * @date 2020/4/22 14:31
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceProviderSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceProviderSysApplication.class, args);
    }

}
