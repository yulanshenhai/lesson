package com.xiao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@MapperScan("com.xiao.mapper")
@SpringBootApplication
public class UserBackgroundApp {
    public static void main(String[] args) {
        SpringApplication.run(UserBackgroundApp.class, args);
    }
}
