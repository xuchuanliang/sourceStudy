package com.ant.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("用户基础")
@Data
public class ParentUser {
    @ApiModelProperty("id")
    private String id;
}
