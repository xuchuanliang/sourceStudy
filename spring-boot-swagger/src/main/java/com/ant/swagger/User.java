package com.ant.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "用户信息",description = "这是描述信息")
@Data
public class User extends ParentUser{
    @ApiModelProperty(value = "名称",required = true)
    private String name;
    @ApiModelProperty(value = "性别（枚举）",required = true)
    private String gender;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("孩子")
    private User child;
}
