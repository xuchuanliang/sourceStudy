package com.hkbt.blobTest;

import java.util.List;

public interface BlobMapper {
    List<String> getIds();

    void updates(List<String> ids);
}
