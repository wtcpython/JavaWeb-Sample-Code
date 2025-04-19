package com.wtc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wtc.utils.AliyunOSSOperator;
import com.wtc.utils.AliyunOSSProperties;

@Configuration
public class CommonConfig {

    @Bean
    AliyunOSSOperator aliyunOSSOperator(AliyunOSSProperties aliyunOSSProperties) {
        return new AliyunOSSOperator(aliyunOSSProperties);
    }

}
