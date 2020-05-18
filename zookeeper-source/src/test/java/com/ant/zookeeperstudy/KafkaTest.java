package com.ant.zookeeperstudy;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import java.util.Arrays;
import java.util.Properties;
import java.util.Random;

public class KafkaTest {

    @Test
    public void testProducer() throws InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.109.130:9092,192.168.109.131:9092,192.168.109.132:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 1000000; i++){
            producer.send(new ProducerRecord<String, String>("xcl-topic", Integer.toString(i), Integer.toString(i)));
            Thread.sleep(new Random().nextInt(3));
        }
        producer.close();
    }

    @Test
    public void testConsumerG1() throws InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.109.130:9092,192.168.109.131:9092,192.168.109.132:9092");
        props.put("group.id", "test1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("xcl-topic"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.err.printf("testConsumerG1========================="+"offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            Thread.sleep(new Random().nextInt(5));
        }
    }

    @Test
    public void testConsumer2G1() throws InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.109.130:9092,192.168.109.131:9092,192.168.109.132:9092");
        props.put("group.id", "test1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("xcl-topic"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.err.printf("testConsumer2G1========================="+"offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            Thread.sleep(new Random().nextInt(5));
        }
    }

    @Test
    public void testConsumer1G12(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.109.130:9092,192.168.109.131:9092,192.168.109.132:9092");
        props.put("group.id", "test2");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("xcl-topic"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.err.printf("testConsumer1G12========================="+"offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
        }
    }
}
