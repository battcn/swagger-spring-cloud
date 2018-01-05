package com.battcn.swagger.smaple;

import com.battcn.swagger.annotation.EnableSwaggerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Levin
 * @since 2017/12/30 0030
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwaggerServer
public class Application3 {

    public static void main(String[] args) {
        SpringApplication.run(Application3.class, args);
    }
}
