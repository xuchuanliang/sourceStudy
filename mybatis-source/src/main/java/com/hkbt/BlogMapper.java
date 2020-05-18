package com.hkbt;

import org.apache.ibatis.annotations.Param;

public interface BlogMapper {
    Blog selectBlog(String name, Integer id);
}
