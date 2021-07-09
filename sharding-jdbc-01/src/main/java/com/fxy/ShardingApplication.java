package com.fxy;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.fxy.dao")
@SpringBootApplication
//@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class,DataSourceAutoConfiguration.class})
public class ShardingApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShardingApplication.class,args);
    }
}
