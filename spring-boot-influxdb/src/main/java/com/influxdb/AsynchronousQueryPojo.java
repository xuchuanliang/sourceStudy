package com.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;

/**
 * 异步的pojo查询方式
 * 参考文档：https://github.com/influxdata/influxdb-client-java/tree/master/client#management-api
 */
public class AsynchronousQueryPojo {
    private static char[] token = "BUvLyqRRlCVLNGZ5CkPhe5y_449JyHUv_sH-T-CoMusXHCudyAI-DmsCl9GQJiJ3-yR8Rmb7ydZNSvaCYK3Rbg==".toCharArray();
    private static String org = "hkbt";
    public static void main(String[] args) throws InterruptedException {
        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://192.168.177.150:8086",token,org);
        //查询语句
        String flux = "from(bucket:\"hkbt\") |> range(start: 0) |> filter(fn: (r) => r._measurement == \"temperature\")";
        //映射到pojo
        QueryApi queryApi = influxDBClient.getQueryApi();
        queryApi.query(flux, Temperature.class,(cancellable, temperature) -> {
            /**
             * The callback to consume a FluxRecord mapped to POJO.
             * cancelable - object has the cancel method to stop asynchronous query
             */
            System.out.println(temperature);
        });
        Thread.sleep(5000);
        influxDBClient.close();
    }
}
