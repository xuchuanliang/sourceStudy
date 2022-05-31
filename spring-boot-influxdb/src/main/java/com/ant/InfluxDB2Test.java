package com.ant;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.Query;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.FluxTable;

import java.time.Instant;
import java.util.List;

public class InfluxDB2Test {
    public static void main(String[] args) {
        // You can generate a Token from the "Tokens Tab" in the UI
        String token = "ILx3VuwndLMA-4z_RU4rTvlX0W-j-FiXgr5vZTCPwvIM5WUcBGylhxkPfWcIni81_7bxxPlNCoBof8Pl1Giekw==";
        String bucket = "test";
        String org = "hkbt";
        InfluxDBClient client = InfluxDBClientFactory.create("http://10.100.22.225:8086",token.toCharArray());
        //Option 1: Use InfluxDB Line Protocol to write data
//        String data = "mem,host=host1 used_percent=23.43234543";
//        try (WriteApi writeApi = client.getWriteApi()) {
//            writeApi.writeRecord(bucket, org, WritePrecision.NS, data);
//        }
        //Option 2: Use a Data Point to write data
//        Point point = Point.measurement("mem").addTag("host","host1").addField("used_percent",23.43234543).time(Instant.now(),WritePrecision.NS);
//        try(WriteApi writeApi = client.getWriteApi()){
//            writeApi.writePoint(bucket,org,point);
//        }
        //Option 3: Use POJO and corresponding class to write data
//        Mem mem = new Mem();
//        mem.host="host1";
//        mem.used_percent = 23.43234543;;
//        mem.time = Instant.now();
//        try (WriteApi writeApi = client.getWriteApi()) {
//            writeApi.writeMeasurement(bucket, org, WritePrecision.NS, mem);
//        }
        //Execute a Flux query
        String query = String.format("from(bucket: \"%s\") |> range(start: -1h)", bucket);
        List<FluxTable> tables = client.getQueryApi().query(query,org);
        System.out.println(tables.size());
        tables.forEach(t->{
            System.out.println(t);
        });
    }
}

