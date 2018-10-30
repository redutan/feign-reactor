package com.example.feignreactor.application;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "first-fail", url = "${retry.url}")
public interface FirstFailClient {

    @GetMapping("/first-fail/{uid}")
    HttpbinResponse get(@PathVariable("uid") String uid);
}
