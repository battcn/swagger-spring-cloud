package com.battcn.swagger.smaple;

import com.battcn.swagger.annotation.EnableSwaggerClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Levin
 * @create 2017/12/30 0030
 */
@SpringBootApplication
@EnableEurekaClient
@EnableSwaggerClient
public class Application1 {

    public static void main(String[] args) {
        SpringApplication.run(Application1.class, args);
    }
}
