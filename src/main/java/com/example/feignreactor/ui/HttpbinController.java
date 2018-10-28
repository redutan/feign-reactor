package com.example.feignreactor.ui;

import com.example.feignreactor.application.HttpbinResponse;
import com.example.feignreactor.application.HttpbinService;
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

    @RequestMapping
    public HttpbinResponse get() {
        return httpbinService.findOne();
    }

    @RequestMapping("/5x4")
    public List<HttpbinResponse> delay5() {
        return httpbinService.find5Delay4();
    }
}
