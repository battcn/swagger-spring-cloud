package com.battcn.swagger.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.battcn.swagger.model.CloudSwaggerResource;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger.web.SwaggerResource;

import java.util.List;

/**
 * @author Levin
 * @since 2017/12/30 0030
 */
@Slf4j
@RestController
public class SwaggerController {

    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;

    @Autowired
    public SwaggerController(DiscoveryClient discoveryClient, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    @GetMapping(value = "/cloud-swagger-resources", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
                log.info(JSON.toJSONString(service));
                JSONObject object = JSON.parseObject(JSON.toJSONString(service));
                JSONObject instanceInfo = object.getJSONObject("instanceInfo");
                String status = instanceInfo.getString("status");
                if (!StringUtils.equalsIgnoreCase(status, "UP")) {
                    continue;
                }
                List<SwaggerResource> swaggerResourceArrayList = Lists.newArrayList();
                try {
                    ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://" + serviceId + "/swagger-resources", HttpMethod.GET, null, String.class);
                    if (responseEntity == null || responseEntity.getStatusCode() != HttpStatus.OK) {
                        continue;
                    }
                    String body = responseEntity.getBody();
                    List<SwaggerResource> swaggerResources = JSON.parseArray(body, SwaggerResource.class);
                    for (SwaggerResource swaggerResource : swaggerResources) {
                        swaggerResource.setName(swaggerResource.getName());
                        swaggerResource.setLocation(service.getUri() + swaggerResource.getLocation());
                        swaggerResource.setSwaggerVersion(swaggerResource.getSwaggerVersion());
                        swaggerResourceArrayList.add(swaggerResource);
                    }
                    cloudSwaggerResource.setServiceInstances(instances);
                    cloudSwaggerResource.setSwaggerResources(swaggerResourceArrayList);
                    list.add(cloudSwaggerResource);
                } catch (HttpClientErrorException e1) {
                    log.error("[{}] - [404] - [已跳过]", serviceId);
                } catch (Exception ex) {
                    if (ex instanceof java.net.ConnectException || ex instanceof org.springframework.web.client.ResourceAccessException) {
                        log.error("[{}] - [服务DOWN] - [已跳过]", serviceId);
                    } else {
                        log.error("[{}] - [{}]", ex);
                    }
                }
            }
        }
        return list;
    }


}
