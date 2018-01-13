package com.battcn.swagger.smaple.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Levin
 * @since  2018/1/12 0012
 */
@ApiModel
public class Student {

    @ApiModelProperty(value = "编号",required = true)
    private int id;
    private String name;
    private Long[] clazz;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long[] getClazz() {
        return clazz;
    }

    public void setClazz(Long[] clazz) {
        this.clazz = clazz;
    }
}
