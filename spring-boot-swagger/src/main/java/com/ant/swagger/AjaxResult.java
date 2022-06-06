package com.ant.swagger;

import lombok.Data;

@Data
public class AjaxResult<T> {
    private String msg;
    private int code;
    private T data;

}
