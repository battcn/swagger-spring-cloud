package com.battcn.swagger.smaple.controller;

import com.battcn.swagger.smaple.entity.Student;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;


/**
 * @author Levin
 * @since 2017/12/30 0030
 */
@RestController
@RequestMapping("/students")
@Api(tags = "1.1", description = "学生管理")
public class StudentController {

    @GetMapping
    public String get() {
        return "{name:\"battcn\"}";
    }

    @PostMapping
    public Student post(Student student) {
        return student;
    }

    @PutMapping
    public Student put(Student student) {
        return student;
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
