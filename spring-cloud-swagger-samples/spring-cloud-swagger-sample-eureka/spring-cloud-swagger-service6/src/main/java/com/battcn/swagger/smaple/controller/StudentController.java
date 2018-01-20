package com.battcn.swagger.smaple.controller;

import com.battcn.swagger.smaple.entity.Student;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


/**
 * @author Levin
 * @since 2017/12/30 0030
 */
@RestController
@RequestMapping("/students")
@Api(tags = "1.1", description = "学生管理")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @GetMapping
    public String get() {
        logger.info("students get ");
        return "{name:\"battcn\"}";
    }

    @PostMapping
    public Student post(@RequestBody Student student) {
        logger.info("students post ");
        return student;
    }

    @PutMapping
    public Student put(@RequestBody Student student) {
        logger.info("students put ");
        return student;
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable("id") Integer id) {
        logger.info("students delete {}", id);
        return id;
    }

    @PatchMapping("/{id}")
    public int patch(@PathVariable("id") Integer id) {
        logger.info("students patch {}", id);
        return id;
    }
}
