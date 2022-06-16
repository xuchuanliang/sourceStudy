package com.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

import java.util.List;

/**
 * 同步查询
 * The synchronous query is not intended for large query results because the Flux response can be potentially unbound.
 * 参考文档：https://github.com/influxdata/influxdb-client-java/tree/master/client#management-api
 */
public class SynchronousQuery {
    private static char[] token = "BUvLyqRRlCVLNGZ5CkPhe5y_449JyHUv_sH-T-CoMusXHCudyAI-DmsCl9GQJiJ3-yR8Rmb7ydZNSvaCYK3Rbg==".toCharArray();
    private static String org = "hkbt";
    public static void main(String[] args){
        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://192.168.177.150:8086",token,org);
        String flux = "from(bucket:\"hkbt\") |> range(start: 0)";
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> fluxTables = queryApi.query(flux);
        int num = 0;
        for(FluxTable fluxTable:fluxTables){
            List<FluxRecord> records = fluxTable.getRecords();
            for(FluxRecord fluxRecord:records){
                System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value"));
                num++;
            }
        }
        System.out.println(num);
        influxDBClient.close();
    }
}
