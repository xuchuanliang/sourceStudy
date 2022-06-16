package com.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;

/**
 * 异步查询
 * 参考文档：https://github.com/influxdata/influxdb-client-java/tree/master/client#management-api
 */
public class AsynchronousQuery {
    private static char[] token = "BUvLyqRRlCVLNGZ5CkPhe5y_449JyHUv_sH-T-CoMusXHCudyAI-DmsCl9GQJiJ3-yR8Rmb7ydZNSvaCYK3Rbg==".toCharArray();
    private static String org = "hkbt";
    public static void main(String[] args) throws InterruptedException {
        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://192.168.177.150:8086",token,org);
        //查询语句
        String flux = "from(bucket:\"hkbt\") |> range(start: 0)";
        QueryApi queryApi = influxDBClient.getQueryApi();
        //异步查询数据
        queryApi.query(flux,(cancellable, fluxRecord) -> {
            /**
             * The callback to consume a FluxRecord.
             * cancellable：object has the cancel method to stop asynchronous query
             *
             */
            System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value"));
        },throwable -> {
            /**
             * The callback to consume any error notification.
             */
            System.out.println("Error occurred: " + throwable.getMessage());
        },()->{
            /**
             * The callback to consume a notification about successfully end of stream.
             */
            System.out.println("Query completed");
        });
        Thread.sleep(5000);
        influxDBClient.close();
    }
}
