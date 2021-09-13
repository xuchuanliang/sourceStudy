package com.ant.springbootkafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {
    // 创建一个名为testtopic的Topic并设置分区数为3，分区副本数为2
    @Bean
    public NewTopic initialTopic() {
        return new NewTopic("testtopic",3, (short) 2 );
    }
    //如果要修改分区数，只需要修改配置重启项目即可
    //修改分区数并不会导致数据的丢失，但是分区数只能增大不能减小
    @Bean
    public NewTopic updateTopic() {
        return new NewTopic("testtopic",5, (short) 2 );
    }

}
