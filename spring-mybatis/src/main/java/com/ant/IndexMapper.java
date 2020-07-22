package com.ant;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface IndexMapper {

    @Select("select * from uc_user")
    List<Map> list();
}
