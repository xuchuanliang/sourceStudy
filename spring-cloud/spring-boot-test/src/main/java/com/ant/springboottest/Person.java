package com.ant.springboottest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class Person {
    @ApiModelProperty()
    private String id;
    private String name;
}
