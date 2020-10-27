package com.hkbt;

import org.apache.ibatis.annotations.Param;

public interface BlogMapper {
    Blog selectBlog(@Param("name") String name, @Param("id") Integer id);
}
