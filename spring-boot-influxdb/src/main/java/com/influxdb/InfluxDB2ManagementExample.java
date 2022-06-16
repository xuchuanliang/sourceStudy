package com.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.domain.*;

import java.util.Arrays;

public class InfluxDB2ManagementExample {
    private static char[] token = "BUvLyqRRlCVLNGZ5CkPhe5y_449JyHUv_sH-T-CoMusXHCudyAI-DmsCl9GQJiJ3-yR8Rmb7ydZNSvaCYK3Rbg==".toCharArray();

    public static void main(final String[] args) {

        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://192.168.177.150:8086", token);

        //
        // Create bucket "iot_bucket" with data retention set to 3,600 seconds
        //
        BucketRetentionRules retention = new BucketRetentionRules();
        retention.setEverySeconds(3600);

        Bucket bucket = influxDBClient.getBucketsApi().createBucket("iot-bucket", retention, "hkbt");

        //
        // Create access token to "iot_bucket"
        //
        PermissionResource resource = new PermissionResource();
        resource.setId(bucket.getId());
        resource.setOrgID("12bdc4164c2e8141");
        resource.setType(PermissionResource.TYPE_BUCKETS);

        // Read permission
        Permission read = new Permission();
        read.setResource(resource);
        read.setAction(Permission.ActionEnum.READ);

        // Write permission
        Permission write = new Permission();
        write.setResource(resource);
        write.setAction(Permission.ActionEnum.WRITE);

        Authorization authorization = influxDBClient.getAuthorizationsApi()
                .createAuthorization("12bdc4164c2e8141", Arrays.asList(read, write));

        //
        // Created token that can be use for writes to "iot_bucket"
        //
        String token = authorization.getToken();
        System.out.println("Token: " + token);

        influxDBClient.close();
    }
}
