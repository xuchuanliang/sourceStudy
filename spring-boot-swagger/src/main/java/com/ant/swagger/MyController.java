package com.ant.swagger;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "我的模块")
@RestController
@RequestMapping("/my")
public class MyController {
    @ApiOperation(value = "获取字符串-V1.0-新增",notes = "用户测试swagger注解，获取字符串的接口")
    @ApiImplicitParam(name = "str",value = "字符串",required = true,defaultValue = "123")
    @GetMapping("/getStr")
    public AjaxResult<String> getStr(String str){
        return new AjaxResult<>();
    }

    @ApiOperation(value = "获取字符串列表-V1.0-修改",notes = "什么模块什么功能使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "str",value = "字符串1",required = true,defaultValue = "NULL"),
            @ApiImplicitParam(name = "str2",value = "字符串2")
    })
    @GetMapping("/getStrList")
    public AjaxResult<List<String>> getStrList(@RequestParam(value = "str",defaultValue = "张三") String str1,String str2){
        return new AjaxResult<List<String>>();
    }

    @ApiOperation(value = "获取用户对象-V1.0-新增",notes = "什么模块什么功能使用")
    @PostMapping("/getStrObj")
    public AjaxResult<User> getStrObj(@RequestBody User user){
        return new AjaxResult<>();
    }
}
