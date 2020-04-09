package com.dj.mall.pro.dict;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @描述
 * @创建人 zhangjq
 * @创建时间 2020/3/24
 */
@EnableDubbo
@SpringBootApplication
@MapperScan("com.dj.mall.mapper")
public class DictApplication {
    public static void main(String[] args) {
        SpringApplication.run(DictApplication.class, args);
    }
}
