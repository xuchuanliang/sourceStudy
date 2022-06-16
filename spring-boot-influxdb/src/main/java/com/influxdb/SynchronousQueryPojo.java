package com.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;

import java.util.List;

/**
 * 通过influxdb 查询结果映射称为pojo
 * 参考文档：https://github.com/influxdata/influxdb-client-java/tree/master/client#management-api
 */
public class SynchronousQueryPojo {
    private static char[] token = "BUvLyqRRlCVLNGZ5CkPhe5y_449JyHUv_sH-T-CoMusXHCudyAI-DmsCl9GQJiJ3-yR8Rmb7ydZNSvaCYK3Rbg==".toCharArray();
    private static String org = "hkbt";
    private static String temperature = "temperature";

    public static void main(String[] args) {
        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://192.168.177.150:8086", token, org);
        //查询语句
        String flux = "from(bucket:\"hkbt\") |> range(start: 0) |> filter(fn: (r) => r._measurement == \"" + temperature + "\")";
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<Temperature> temperatures = queryApi.query(flux, Temperature.class);
        for(Temperature temperature:temperatures){
            System.out.println(temperature);
        }
        influxDBClient.close();
    }
}
