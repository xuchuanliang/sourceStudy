package com.influxdb;

import com.influxdb.query.dsl.Flux;

public class SynchronousQueryDSL {
    public static void main(String[] args) {
        Flux flux = Flux.from("hkbt");

    }
}
