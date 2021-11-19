package com.ant.springboottest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("相关文档")
@RestController
@RequestMapping("/impl")
public class ControllerImpl {
    @GetMapping("/test")
    public String test(){
        return "String";
    }

    @ApiOperation("测试")
    @GetMapping("/test1")
    public boolean test1(@ApiParam("shuju") @RequestParam("n") String n){
        System.out.println("get param:"+n);
        return true;
    }
}
