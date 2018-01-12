package com.battcn.swagger.smaple.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author Levin
 * @since 2018/1/12 0012
 */
@ApiModel
public class School {

    @ApiModelProperty("编号")
    private long id;

    @ApiModelProperty("学生")
    private Student student;

    @ApiModelProperty("学生集合")
    private List<Student> students;

}
