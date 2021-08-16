package com.test1.ant;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface IndexMapper {

    @Select("select * from blog")
    List<Map> list();
}
