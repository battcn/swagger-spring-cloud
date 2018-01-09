package com.battcn.swagger.smaple.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Levin
 * @since 2017/12/30 0030
 */
@RestController
@RequestMapping("/schools")
@Api(tags = "1.2", description = "学校管理")
public class SchoolController {


    @GetMapping
    public String get() {
        return "{name:\"battcn\"}";
    }

    @PostMapping
    public int add() {
        return 200;
    }

}
