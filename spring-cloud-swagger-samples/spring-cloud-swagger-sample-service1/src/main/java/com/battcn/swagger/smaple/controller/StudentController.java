package com.battcn.swagger.smaple.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger.web.SwaggerResource;

import java.util.List;

/**
 * @author Levin
 * @create 2017/12/30 0030
 */
@RestController
@RequestMapping("/students")
@Api(tags = "1.1", description = "学生管理")
public class StudentController {

    private final DiscoveryClient discoveryClient;

    @Autowired
    public StudentController(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @GetMapping
    public String getStudent() {
        List<String> services = discoveryClient.getServices();
        List<ServiceInstance> instances1 = discoveryClient.getInstances("spring-cloud-swagger-service2");
        System.out.println(instances1.size());
        if (CollectionUtils.isEmpty(services)) {
            return null;
        }
        SwaggerResource resource = new SwaggerResource();
        for (String serviceId : services) {
            List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        }
        return "{name:\"battcn\"}";
    }


}
