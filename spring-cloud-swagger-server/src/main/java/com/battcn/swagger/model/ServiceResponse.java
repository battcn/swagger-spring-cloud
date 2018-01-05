package com.battcn.swagger.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.ResponseEntity;

/**
 * @author Levin
 * @since 2018/1/5 0005
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse {

    private ResponseEntity<String> response;
    private ServiceInstance serviceInstance;

}
