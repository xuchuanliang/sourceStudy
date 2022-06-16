package com.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.exceptions.InfluxException;

import java.time.Instant;

/**
 * 同步阻塞写数据
 *
 */
public class WriteDataBlocking {
    private static char[] token = "BUvLyqRRlCVLNGZ5CkPhe5y_449JyHUv_sH-T-CoMusXHCudyAI-DmsCl9GQJiJ3-yR8Rmb7ydZNSvaCYK3Rbg==".toCharArray();
    private static String org = "hkbt";
    private static String bucket = "hkbt";

    public static void main(String[] args) {
        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://192.168.177.150:8086",token,org,bucket);
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
        try {
            //
            // Write by LineProtocol
            //
            String record = "temperature,location=north value=60.0";

            writeApi.writeRecord(WritePrecision.NS, record);

            //
            // Write by Data Point
            //
            Point point = Point.measurement("temperature")
                    .addTag("location", "west")
                    .addField("value", 55D)
                    .time(Instant.now().toEpochMilli(), WritePrecision.MS);

            writeApi.writePoint(point);

            //
            // Write by POJO
            //
            Temperature temperature = new Temperature();
            temperature.location = "south";
            temperature.value = 62D;
            temperature.time = Instant.now();

            writeApi.writeMeasurement(WritePrecision.NS, temperature);

        } catch (InfluxException ie) {
            System.out.println("InfluxException: " + ie);
        }

        influxDBClient.close();
    }
}
