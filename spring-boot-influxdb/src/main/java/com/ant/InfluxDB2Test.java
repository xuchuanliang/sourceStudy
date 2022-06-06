package com.ant;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.*;
import com.influxdb.client.domain.Query;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class InfluxDB2Test {
    public static void main(String[] args) {
        read(insert());
    }

    private static void t1() {
        // You can generate a Token from the "Tokens Tab" in the UI
        String token = "ILx3VuwndLMA-4z_RU4rTvlX0W-j-FiXgr5vZTCPwvIM5WUcBGylhxkPfWcIni81_7bxxPlNCoBof8Pl1Giekw==";
        String bucket = "test";
        String org = "hkbt";
        InfluxDBClient client = InfluxDBClientFactory.create("http://10.100.22.225:8086", token.toCharArray());
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
        List<FluxTable> tables = client.getQueryApi().query(query, org);
        System.out.println(tables.size());
        tables.forEach(t -> {
            System.out.println(t);
        });
    }

    public static InfluxDBClient influxDBClient() {
        String url = "http://10.100.22.225:8086";
        String bucket = "test";
        String org = "hkbt";
        String token = "sPdKvTpFDym0FgTl-DpOeJ7T-hEpuDmeFJO7nagwuRpuUjW8V8MK7lwVa_DqvHVq_UnPU8FR0sdBCQ0xQHwY7A==";
        return InfluxDBClientFactory.create(url, token.toCharArray(), org, bucket);
    }

    private static String insert(){
        InfluxDBClient client = influxDBClient();
        WriteApiBlocking writeApiBlocking = client.getWriteApiBlocking();
        Sensor sensor = new Sensor();
        String id = "id_" + UUID.randomUUID().toString();
        sensor.setSensor_id(id);
        sensor.setLocation("location_" + UUID.randomUUID().toString());
        sensor.setModel_number("number_"+UUID.randomUUID().toString());
        sensor.setLast_inspected(Instant.now());
        writeApiBlocking.writeMeasurement(WritePrecision.NS,sensor);
        return id;
    }

    private static void read(String id){
        String bucket = "test";
        /*
        from(bucket:"bucketName")
            |> range(start: -1h)
            |> filter(fn: (r) => r._measurement == )
         */
        String flux = String.format( "from(bucket:\"%s\") |> range(start:0) |> filter(fn: (r) => r[\"_measurement\"] == \"sensor\") |> filter(fn: (r) => r[\"sensor_id\"] == \"TLM0100\"or r[\"sensor_id\"] == \"TLM0101\" or r[\"sensor_id\"] == \"TLM0103\" or r[\"sensor_id\"] == \""+id+"\") |> sort() |> yield(name: \"sort\")", bucket);
        InfluxDBClient client = influxDBClient();
        QueryApi queryApi = client.getQueryApi();
        List<FluxTable> query = queryApi.query(flux);
        for(FluxTable fluxTable : query){
            List<FluxRecord> records = fluxTable.getRecords();
            for(FluxRecord fluxRecord:records){
                System.out.println(fluxRecord.getValueByKey("sensor_id"));
                System.out.println(fluxRecord.getValueByKey("location"));
                System.out.println(fluxRecord.getValueByKey("model_number"));
                System.out.println(fluxRecord.getValueByKey("last_inspected"));
            }
        }
    }
}

@Measurement(name = "sensor")
@Data
class Sensor {
    @Column(tag = true)
    String sensor_id;

    @Column
    String location;

    @Column
    String model_number;

    @Column(timestamp = true)
    Instant last_inspected;
}

