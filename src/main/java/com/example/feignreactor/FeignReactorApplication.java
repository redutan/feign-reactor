package com.example.feignreactor;

import feign.Retryer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableHystrix
@EnableFeignClients
@SpringBootApplication
public class FeignReactorApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeignReactorApplication.class, args);
    }
}
