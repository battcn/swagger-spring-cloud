package com.battcn.swagger.smaple.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Levin
 * @since 2018/1/29 0029
 */
@ApiModel
public class RoleDto {

    @ApiModelProperty(value = "角色ID")
    private Long id;
    @ApiModelProperty(value = "角色名称")
    private String name;
    @ApiModelProperty(value = "角色描述")
    private String description;
    @ApiModelProperty(value = "是否可用")
    private Boolean active;
    @ApiModelProperty(value = "员工数量")
    private Integer staffTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getStaffTotal() {
        return staffTotal;
    }

    public void setStaffTotal(Integer staffTotal) {
        this.staffTotal = staffTotal;
    }
}
