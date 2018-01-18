package com.battcn.swagger.smaple.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author Levin
 * @since  2018/1/12 0012
 */
@ApiModel(value = "Student", description = "学生")
public class Student {

    @ApiModelProperty(value = "编号",required = true)
    private int id;
    @ApiModelProperty(value = "名称",required = true)
    private String name;
    @ApiModelProperty(value = "班级",required = true)
    private Long[] clazz;
    @ApiModelProperty(value = "地址",required = true)
    private List<Address> addresses;

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

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
