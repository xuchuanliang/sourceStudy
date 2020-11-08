package com.hkbt;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogMapper {
    Blog selectBlog(@Param("name") String name, @Param("id") Integer id);
    void inserBlog(@Param("name") String name);
    List<Blog> findAll();
}
