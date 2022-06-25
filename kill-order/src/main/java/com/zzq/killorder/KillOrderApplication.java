package com.zzq.killorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.zzq.killorder.mapper")
@SpringBootApplication
public class KillOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(KillOrderApplication.class, args);
    }

}
