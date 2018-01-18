package com.battcn.swagger.smaple.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Levin
 * @since  2018-01-18.
 */
@ApiModel
public class Address {

    @ApiModelProperty(value = "名称",required = true)
    private String name;
    @ApiModelProperty(value = "状态",required = true)
    private Boolean state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
