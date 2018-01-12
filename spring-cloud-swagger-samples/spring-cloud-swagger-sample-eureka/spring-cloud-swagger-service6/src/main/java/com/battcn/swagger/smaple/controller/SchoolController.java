package com.battcn.swagger.smaple.controller;

import com.battcn.swagger.smaple.entity.School;
import com.battcn.swagger.smaple.entity.Student;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

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
        return "{name:\"school\"}";
    }

    @PostMapping
    public School post(School school) {
        return school;
    }

    @PutMapping
    public School put(School school) {
        return school;
    }

    @DeleteMapping
    public int delete(@PathVariable("id") Integer id) {
        return id;
    }
    @PatchMapping
    public int patch(@PathVariable("id") Integer id) {
        return id;
    }

}
