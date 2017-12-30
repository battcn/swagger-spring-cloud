package com.battcn.swagger.controller;

import com.alibaba.fastjson.JSON;
import com.battcn.swagger.model.CloudSwaggerResource;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger.web.SwaggerResource;

import java.util.List;

/**
 * @author Levin
 * @create 2017/12/30 0030
 */
@RestController
public class SwaggerController {

    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;

    @Autowired
    public SwaggerController(DiscoveryClient discoveryClient, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    @GetMapping(value = "/swagger-resources", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<CloudSwaggerResource> test() {
        List<CloudSwaggerResource> list = Lists.newArrayList();
        List<String> services = discoveryClient.getServices();
        if (CollectionUtils.isEmpty(services)) {
            return null;
        }
        for (String serviceId : services) {
            CloudSwaggerResource cloudSwaggerResource = new CloudSwaggerResource();
            List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
            if (CollectionUtils.isEmpty(instances)) {
                continue;
            }
            for (ServiceInstance service : instances) {
                List<SwaggerResource> swaggerResourceArrayList = Lists.newArrayList();
                System.out.println(service.getServiceId());
                String text = this.restTemplate.getForObject("http://" + serviceId + "/swagger-resources", String.class);
                List<SwaggerResource> swaggerResources = JSON.parseArray(text, SwaggerResource.class);
                for (SwaggerResource swaggerResource : swaggerResources) {
                    swaggerResource.setName(swaggerResource.getName());
                    swaggerResource.setLocation(service.getUri() + swaggerResource.getLocation());
                    swaggerResource.setSwaggerVersion(swaggerResource.getSwaggerVersion());
                    swaggerResourceArrayList.add(swaggerResource);
                }
                cloudSwaggerResource.setServiceInstances(instances);
                cloudSwaggerResource.setSwaggerResources(swaggerResourceArrayList);
                list.add(cloudSwaggerResource);
            }
        }
        return list;
    }


}
