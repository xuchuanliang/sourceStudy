package com.ant.validate;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@Data
public class Person3 {
    @Range(min = 0L,max = 100L)
    private Long id;
    @NotBlank
    private String name;
    private String email;
    private String phoneNum;
    private String address;
    @Range(min = 10,max = 200)
    private Integer age;
}
