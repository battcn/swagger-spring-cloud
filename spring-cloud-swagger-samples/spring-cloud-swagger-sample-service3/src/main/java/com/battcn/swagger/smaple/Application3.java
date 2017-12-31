package com.battcn.swagger.smaple;

import com.battcn.swagger.annotation.EnableSwaggerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Levin
 * @create 2017/12/30 0030
 */
@SpringBootApplication
@EnableEurekaClient
@EnableSwaggerServer
public class Application3 {

    public static void main(String[] args) {
        SpringApplication.run(Application3.class, args);
    }
}
