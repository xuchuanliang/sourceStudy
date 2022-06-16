package com.ant.validate;

import lombok.Data;

@Data
public class Page<T> {
    private int pageNo;
    private int pageSize;
    private T data;
}
