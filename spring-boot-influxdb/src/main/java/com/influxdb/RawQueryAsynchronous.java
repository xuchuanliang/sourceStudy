package com.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;

/**
 * 允许逐行处理数据
 * 参考地址：https://github.com/influxdata/influxdb-client-java/tree/master/client#management-api
 */
public class RawQueryAsynchronous {
    private static char[] token = "BUvLyqRRlCVLNGZ5CkPhe5y_449JyHUv_sH-T-CoMusXHCudyAI-DmsCl9GQJiJ3-yR8Rmb7ydZNSvaCYK3Rbg==".toCharArray();
    private static String org = "hkbt";
    public static void main(String[] args) throws InterruptedException {
        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://192.168.177.150:8086",token,org);
        //查询语句
        String flux = "from(bucket:\"hkbt\") |> range(start: 0)";
        QueryApi queryApi = influxDBClient.getQueryApi();
        queryApi.queryRaw(flux,(cancellable, line) -> {
            /**
             * The callback to consume a line of CSV response
             * cancelable - object has the cancel method to stop asynchronous query
              */
            System.out.println("Response: " + line);
        });
        Thread.sleep(5000);
        influxDBClient.close();
    }
}
