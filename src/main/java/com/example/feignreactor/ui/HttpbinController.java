package com.example.feignreactor.ui;

import com.example.feignreactor.application.HttpbinResponse;
import com.example.feignreactor.application.HttpbinService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/httpbin")
public class HttpbinController {
    private final HttpbinService httpbinService;

    public HttpbinController(HttpbinService httpbinService) {
        this.httpbinService = httpbinService;
    }

    @GetMapping
    public HttpbinResponse get() {
        return httpbinService.findOne();
    }

    @GetMapping("/5x4")
    public List<HttpbinResponse> delay5() {
        return httpbinService.find5Delay4();
    }

    @GetMapping("/feign/fallback")
    public HttpbinResponse fallback() {
        return httpbinService.notFoundToFallback();
    }

    @GetMapping("/hystrix/fallback")
    public HttpbinResponse fallback2() {
        return httpbinService.notFoundOnHystrix();
    }

    @GetMapping("/exception")
    public HttpbinResponse exception() {
        return httpbinService.notFoundToException();
    }

    @GetMapping("/retry")
    public HttpbinResponse retryByFeign() {
        return httpbinService.retryByFeign();
    }
}
