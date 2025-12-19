package com.easy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class EasyToDevelopApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyToDevelopApplication.class, args);
        log.info("easy-to-develop 启动成功...");
    }

}
