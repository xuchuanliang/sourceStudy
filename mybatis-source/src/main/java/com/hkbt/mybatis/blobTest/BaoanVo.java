package com.hkbt.mybatis.blobTest;

public class BaoanVo {
    private String id;
    private String tb_name;
    private String content_id_value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTb_name() {
        return tb_name;
    }

    public void setTb_name(String tb_name) {
        this.tb_name = tb_name;
    }

    public String getContent_id_value() {
        return content_id_value;
    }

    public void setContent_id_value(String content_id_value) {
        this.content_id_value = content_id_value;
    }

    @Override
    public String toString() {
        return "BaoanVo{" +
                "id='" + id + '\'' +
                ", tb_name='" + tb_name + '\'' +
                ", content_id_value='" + content_id_value + '\'' +
                '}';
    }
}
