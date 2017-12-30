package com.battcn.swagger.discovery;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.event.HeartbeatMonitor;
import org.springframework.context.annotation.Configuration;

/**
 * @author Levin
 * @create 2017/12/30 0030
 */
@Slf4j
@Configuration
public class ApplicationDiscoveryListener {


    private final DiscoveryClient discoveryClient;
    private final HeartbeatMonitor monitor = new HeartbeatMonitor();


    public ApplicationDiscoveryListener(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }





}
