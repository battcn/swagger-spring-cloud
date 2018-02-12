package com.battcn.swagger.smaple.controller;

import com.alibaba.fastjson.JSON;
import com.battcn.swagger.properties.ApiDataType;
import com.battcn.swagger.properties.ApiParamType;
import com.battcn.swagger.smaple.entity.Address;
import com.battcn.swagger.smaple.entity.RoleDto;
import com.battcn.swagger.smaple.entity.Student;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Levin
 * @since 2017/12/30 0030
 */
@RestController
@RequestMapping("/students")
@Api(tags = "1.1", description = "学生管理")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @GetMapping("/query")
    public String query(String token, @RequestParam String name, @RequestParam String email) {
        logger.info("students get ");
        return "{name:\"battcn\"}";
    }

    @GetMapping
    @ApiOperation(value = "角色列表 (DONE)")
    public String test1(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                        @RequestParam(value = "page_size", defaultValue = "20") Integer pageSize,
                        @RequestParam(value = "roleName", required = false) String roleName,
                        @RequestParam(value = "active", required = false) Boolean active) {
        return "哈哈";
    }

    @GetMapping("/test2")
    @ApiOperation(value = "角色列表 (DONE)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页（页数）", dataType = ApiDataType.INT, paramType = ApiParamType.QUERY),
            @ApiImplicitParam(name = "page_size", value = "分页（每页大小）", dataType = ApiDataType.INT, paramType = ApiParamType.QUERY)})
    public List<RoleDto> test2(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "page_size", defaultValue = "20") Integer pageSize,
                               @RequestParam(value = "roleName", required = false) String roleName,
                               @RequestParam(value = "active", required = false) Boolean active) {
        return Lists.newArrayList();
    }

    @PostMapping
    public Student post(@RequestBody Student student) {
        logger.info("students post ");

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


    public static void main(String[] args) {
        Student student = new Student();
        student.setId(1);
        student.setClazz(new Long[]{1L, 2L});
        List<Address> list = new ArrayList<>();
        Address address = new Address();
        address.setName("11");
        address.setState(false);
        list.add(address);
        student.setAddresses(list);
        System.out.println(JSON.toJSONString(student));


    }
}
