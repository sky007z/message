package com.laughing.message;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
@Slf4j
@SpringBootApplication
@EnableScheduling
@MapperScan("com.laughing.message.mapper")
public class MessageApplication {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHH");
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(MessageApplication.class, args);
        log.info("项目启动成功，启动时间："+dateFormat.format(new Date()));
    }

}
