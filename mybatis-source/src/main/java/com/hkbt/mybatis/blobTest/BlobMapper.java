package com.hkbt.mybatis.blobTest;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlobMapper {
    List<String> getIds();

    void updates(List<String> ids);

    List<BaoanVo> getInfo(@Param("name") String name,@Param("syncStatus") Integer syncStatus);
}
