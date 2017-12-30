package com.battcn.swagger.smaple.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Levin
 * @create 2017/12/30 0030
 */
@RestController
@RequestMapping("/students")
@Api(tags = "1.1", description = "学生管理")
public class StudentController {

    @GetMapping
    public String getStudent() {
        return "{name:\"battcn\"}";
    }


}
