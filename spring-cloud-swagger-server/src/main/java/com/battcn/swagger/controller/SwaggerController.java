package com.battcn.swagger.controller;

import com.netflix.appinfo.ApplicationInfoManager;
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
    private final ApplicationInfoManager applicationInfoManager;

    @Autowired
    public SwaggerController(ApplicationInfoManager applicationInfoManager, DiscoveryClient discoveryClient, RestTemplate restTemplate) {
        this.applicationInfoManager = applicationInfoManager;
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    @GetMapping(value = "/swagger-resources", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<SwaggerResource> test() {
        List<String> services = discoveryClient.getServices();
        if (CollectionUtils.isEmpty(services)) {
            return null;
        }
        SwaggerResource resource = new SwaggerResource();
        for (String serviceId : services) {
            List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
            List<SwaggerResource> swaggerResources = this.restTemplate.getForObject("http://localhost/" + instances + "/swagger-resources", List.class);
        }
        return null;
    }


}
