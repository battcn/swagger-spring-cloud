package com.battcn.swagger.controller;

import com.battcn.swagger.model.CloudSwaggerResource;
import com.battcn.swagger.model.ServiceResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger.web.SwaggerResource;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.battcn.swagger.constant.SwaggerConstant.*;
import static java.util.stream.Collectors.toList;

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
    public List<CloudSwaggerResource> getDocumentation() {
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
            List<ServiceResponse> serviceResponses = instances.stream()
                    .filter(this::checkHealth)
                    .map(service -> {
                        ServiceResponse response = null;
                        ResponseEntity<String> exchange = exchange(SWAGGER_RESOURCES_URL, serviceId);
                        if (exchange != null) {
                            response = new ServiceResponse(exchange, service);
                        }
                        return response;
                    })
                    .filter(Objects::nonNull)
                    .collect(toList());

            for (ServiceResponse response : serviceResponses) {
                try {
                    String body = response.getResponse().getBody();
                    List<SwaggerResource> swaggerResources;
                    // 代表是XML,需要走XML解析
                    if (response.getResponse().getHeaders().getContentType().isCompatibleWith(MediaType.APPLICATION_XML)) {
                        XmlMapper xmlMapper = new XmlMapper();
                        swaggerResources = xmlMapper.readValue(body, new TypeReference<List<SwaggerResource>>() {
                        });
                    } else {
                        swaggerResources = objectMapper.readValue(body, new TypeReference<List<SwaggerResource>>() {
                        });
                    }
                    List<SwaggerResource> swaggerResourceArrayList = Lists.newArrayList();
                    for (SwaggerResource swaggerResource : swaggerResources) {
                        swaggerResource.setName(swaggerResource.getName());
                        swaggerResource.setLocation(response.getServiceInstance().getUri() + swaggerResource.getLocation());
                        swaggerResource.setSwaggerVersion(swaggerResource.getSwaggerVersion());
                        swaggerResourceArrayList.add(swaggerResource);
                    }
                    cloudSwaggerResource.setServiceInstances(instances);
                    cloudSwaggerResource.setSwaggerResources(swaggerResourceArrayList);
                    list.add(cloudSwaggerResource);
                } catch (IOException ex) {
                    log.error("[JSON转换错误] - [{}]", ex);
                }
            }
        }
        return list;
    }

    /**
     * 检查服务是否健壮
     *
     * @param service 服务信息
     * @return 检查结果
     */
    private boolean checkHealth(ServiceInstance service) {
        try {
            String serviceId = service.getServiceId();
            if (log.isDebugEnabled()) {
                String serviceInfo = objectMapper.writeValueAsString(service);
                log.debug("[服务实例信息] - [{}]", serviceInfo);
            }
            ResponseEntity<String> health = exchange(HEALTH_URL, serviceId);
            String body = health.getBody();
            if (log.isDebugEnabled()) {
                log.debug("[服务] - [{}] - [响应内容] - [{}]", serviceId, body);
            }
            JsonNode healthInfo = objectMapper.readValue(body, JsonNode.class);
            String status = healthInfo.get(STATUS).asText();
            return StringUtils.equalsIgnoreCase(status, UP);
        } catch (Exception e) {
            log.error("[错误信息] - [{}]", e.getMessage());
            return false;
        }
    }

    /**
     * 发送 HTTP 请求,包装了 restTemplate.exchange();
     *
     * @param url       请求地址
     * @param serviceId 服务ID
     * @return 请求结果
     */
    private ResponseEntity<String> exchange(String url, String serviceId) {
        ResponseEntity<String> result = null;
        try {
            result = this.restTemplate.exchange(String.format(url, serviceId), HttpMethod.GET, null, String.class);
        } catch (HttpClientErrorException e1) {
            log.error("[{}] - [请求服务404] - [已跳过]", serviceId);
        } catch (Exception ex) {
            if (ex instanceof org.springframework.web.client.ResourceAccessException) {
                log.error("[{}] - [请求服务DOWN] - [已跳过]", serviceId);
            } else {
                log.error("[错误信息] - [{}]", ex);
            }
        }
        return result;
    }

}
