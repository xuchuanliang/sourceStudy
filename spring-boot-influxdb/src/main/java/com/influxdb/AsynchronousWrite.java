package com.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;

import java.time.Instant;

/**
 * 异步非阻塞写数据
 * The WriteApi is supposed to be use as a singleton. Don't create a new instance for every write!
 * 使用WriteApi支持如下几点：
 * 1。writing data using InfluxDB Line Protocol, Data Point, POJO
 * 2.use batching for writes
 * 3.use client backpressure strategy
 * 4.produces events that allow user to be notified and react to this events
 *   4.1.WriteSuccessEvent - published when arrived the success response from Platform server
 *   4.2.BackpressureEvent - published when is client backpressure applied
 *   4.3.WriteErrorEvent - published when occurs a unhandled exception
 *   4.4.WriteRetriableErrorEvent - published when occurs a retriable error
 * 5.use GZIP compression for data
 */
public class AsynchronousWrite {
    private static char[] token = "BUvLyqRRlCVLNGZ5CkPhe5y_449JyHUv_sH-T-CoMusXHCudyAI-DmsCl9GQJiJ3-yR8Rmb7ydZNSvaCYK3Rbg==".toCharArray();
    private static String org = "hkbt";
    private static String bucket = "hkbt";

    public static void main(String[] args) {
        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://192.168.177.150:8086",token,org,bucket);
        try(WriteApi writeApi = influxDBClient.makeWriteApi()){
            writePOJO(writeApi);
        }
        influxDBClient.close();
    }

    /**
     * 以pojo的格式向influx中写入数据
     * @param writeApi
     */
    private static void writePOJO(WriteApi writeApi){
        Temperature temperature = new Temperature();
        temperature.location = "south";
        temperature.value = 62D;
        temperature.time = Instant.now();

        writeApi.writeMeasurement(WritePrecision.NS, temperature);
    }
}
