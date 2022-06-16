package com.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;

/**
 * 原始查询，查询出csv格式
 * 参考文档：https://github.com/influxdata/influxdb-client-java/tree/master/client#management-api
 */
public class RawQuery {
    private static char[] token = "BUvLyqRRlCVLNGZ5CkPhe5y_449JyHUv_sH-T-CoMusXHCudyAI-DmsCl9GQJiJ3-yR8Rmb7ydZNSvaCYK3Rbg==".toCharArray();
    private static String org = "hkbt";
    public static void main(String[] args) throws InterruptedException {
        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://192.168.177.150:8086",token,org);
        //查询语句
        String flux = "from(bucket:\"hkbt\") |> range(start: 0)";
        QueryApi queryApi = influxDBClient.getQueryApi();
        String csv = queryApi.queryRaw(flux);
        System.out.println("CSV response: " + csv);
        influxDBClient.close();
    }
}
