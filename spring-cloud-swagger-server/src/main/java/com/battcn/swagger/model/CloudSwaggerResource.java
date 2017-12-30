package com.battcn.swagger.model;

import lombok.Data;
import org.springframework.cloud.client.ServiceInstance;
import springfox.documentation.swagger.web.SwaggerResource;

import java.util.List;

/**
 * @author Levin
 * @create 2017/12/30 0030
 */
@Data
public class CloudSwaggerResource {

    private List<ServiceInstance> serviceInstances;
    private List<SwaggerResource> swaggerResources;

}
