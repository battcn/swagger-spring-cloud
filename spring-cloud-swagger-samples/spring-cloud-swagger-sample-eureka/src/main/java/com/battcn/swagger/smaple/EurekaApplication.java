package com.battcn.swagger.smaple;

import com.battcn.swagger.annotation.EnableSwaggerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Levin
 * @create 2017/12/30 0030
 */
@SpringBootApplication
@EnableEurekaServer
@EnableSwaggerServer
@ComponentScan("com.battcn")
public class EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }
}
