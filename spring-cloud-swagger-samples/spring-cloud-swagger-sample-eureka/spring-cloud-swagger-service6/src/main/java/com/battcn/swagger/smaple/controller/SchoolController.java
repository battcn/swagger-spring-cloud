package com.battcn.swagger.smaple.controller;

import com.battcn.swagger.smaple.entity.School;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @author Levin
 * @since 2017/12/30 0030
 */
@RestController
@RequestMapping("/schools")
@Api(tags = "1.2", description = "学校管理")
public class SchoolController {

    private static final Logger logger = LoggerFactory.getLogger(SchoolController.class);


    @GetMapping
    public String get() {
        logger.info("schools get ");
        return "{name:\"school\"}";
    }

    @PostMapping
    public School post(@RequestBody School school) {
        logger.info("schools post ");
        return school;
    }

    @PutMapping
    public School put(@RequestBody School school) {
        logger.info("schools put ");
        return school;
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable("id") Integer id) {
        logger.info("schools delete {}", id);
        return id;
    }

    @PatchMapping("/{id}")
    public int patch(@PathVariable("id") Integer id) {
        logger.info("schools patch {}", id);
        return id;
    }

}
