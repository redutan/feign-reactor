package com.example.feignreactor.application;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "httpbin", url = "${httpbin.url}")
public interface HttpbinNoFallbackClient {

    @GetMapping("/status/{code}")
    HttpbinResponse status(@PathVariable("code") int code);
}

