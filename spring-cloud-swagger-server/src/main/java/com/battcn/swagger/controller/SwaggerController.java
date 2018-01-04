package com.battcn.swagger.controller;

import com.battcn.swagger.model.CloudSwaggerResource;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    @Autowired
    public SwaggerController(DiscoveryClient discoveryClient, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
        this.objectMapper = objectMapper;
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
                try {
                    String serviceInfo = objectMapper.writeValueAsString(service);
                    JsonNode node = objectMapper.readValue(serviceInfo, JsonNode.class);
                    JsonNode instanceInfo = node.get("instanceInfo");
                    String status = instanceInfo.get("status").asText();
                    if (!StringUtils.equalsIgnoreCase(status, "UP")) {
                        continue;
                    }
                } catch (java.io.IOException e) {
                    log.error("[错误信息] - [{}]", e.getMessage());
                    continue;
                }
                List<SwaggerResource> swaggerResourceArrayList = Lists.newArrayList();
                try {
                    ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://" + serviceId + "/swagger-resources", HttpMethod.GET, null, String.class);
                    if (responseEntity == null || responseEntity.getStatusCode() != HttpStatus.OK) {
                        continue;
                    }
                    String body = responseEntity.getBody();
                    List<SwaggerResource> swaggerResources = objectMapper.readValue(body, new TypeReference<List<SwaggerResource>>() {});
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
                        log.error("[错误信息] - [{}]", ex);
                    }
                }
            }
        }
        return list;
    }


}
