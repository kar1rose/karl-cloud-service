package org.karl.sh.service.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *
 * @author KARL.ROSE
 * @date 2020/3/24 2:07 下午
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceMailApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceMailApplication.class, args);
	}

}
