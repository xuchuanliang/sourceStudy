package com.influxdb;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.Data;

import java.time.Instant;

@Data
@Measurement(name = "temperature")
public class Temperature {
    /**
     * tag类型的属性
     */
    @Column(tag = true)
    String location;

    /**
     * 普通field类型的属性
     */
    @Column
    Double value;

    /**
     * time属性（主键）
     */
    @Column(timestamp = true)
    Instant time;


}
