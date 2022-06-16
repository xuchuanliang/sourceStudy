package com.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

import java.time.Instant;
import java.util.List;

/**
 * 向influxdb2.x中读写数据
 * 参考地址：https://github.com/influxdata/influxdb-client-java#writes-and-queries-in-influxdb-2x
 */
public class InfluxDB2Example {
    private static char[] token = "BUvLyqRRlCVLNGZ5CkPhe5y_449JyHUv_sH-T-CoMusXHCudyAI-DmsCl9GQJiJ3-yR8Rmb7ydZNSvaCYK3Rbg==".toCharArray();
    private static String org = "hkbt";
    private static String bucket = "hkbt";

    public static void main(String[] args) {
        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://192.168.177.150:8086",token,org,bucket);
        /**
         * 以不同类型的方式向influxdb中写入数据，一般使用pojo方式
         */
        writePoint(influxDBClient);
        writeRecord(influxDBClient);
        writePOJO(influxDBClient);

        /**
         * 查询数据
         */
        query(influxDBClient);
        influxDBClient.close();
    }

    /**
     * 以Point类型写入数据
     * @param influxDBClient
     */
    private static void writePoint(InfluxDBClient influxDBClient){
        /**
         * 写数据
         */
        WriteApiBlocking writeApiBlocking = influxDBClient.getWriteApiBlocking();
        /**
         * 使用Point类型写入
         * 构造要写入的数据：Point
         */
        Point point = Point
                //measurement相当于表名
                .measurement("temperature")
                //相当于索引：查询速度快，但是值只能是字符串
                .addTag("location", "west")
                //相当于没有索引的普通字段
                .addField("value", 55D)
                //时间戳字段，influxdb中每一个measurement中都有一个时间字段，相当于mysql中表的主键
                .time(Instant.now().getEpochSecond(), WritePrecision.MS);
        /**
         * influxdb中measurement是弱类型，会自动创建，不需要单独创建
         */
        writeApiBlocking.writePoint(point);
    }

    /**
     * 以字符串行的方式写入数据
     */
    private static void writeRecord(InfluxDBClient influxDBClient){
        WriteApiBlocking writeApiBlocking = influxDBClient.getWriteApiBlocking();
        writeApiBlocking.writeRecord(WritePrecision.NS,"temperature,location=north value=60.0");
    }

    /**
     * 以java类对象的方式写入数据
     * @param influxDBClient
     */
    private static void writePOJO(InfluxDBClient influxDBClient){
        Temperature temperature = new Temperature();
        temperature.setLocation("south");
        temperature.setValue(62D);
        temperature.setTime(Instant.now());
        influxDBClient.getWriteApiBlocking().writeMeasurement(WritePrecision.NS,temperature);
    }

    /**
     * 从influxdb中查询数据
     * @param influxDBClient
     */
    private static void query(InfluxDBClient influxDBClient){
        String flux = "from(bucket:\"hkbt\") |> range(start: 0)";
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(flux);
        for(FluxTable fluxTable:tables){
            List<FluxRecord> records = fluxTable.getRecords();
            for(FluxRecord fluxRecord:records){
                System.out.println(fluxRecord.getTime()+":"+fluxRecord.getValueByKey("_value"));
            }
        }
    }

    /**
     * 使用influxdb client 创建一个token
     */
    private static void createToken(){
    }

}
