package com.example.feignreactor.application;

import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "httpbin", url = "${httpbin.url}", fallbackFactory = HttpbinClientFallbackFactory.class)
public interface HttpbinClient {

    @GetMapping("/get")
    HttpbinResponse get();

    @GetMapping("/delay/{delay}")
    HttpbinResponse delay(@PathVariable("delay") int delay);
}

@Component
class HttpbinClientFallbackFactory implements FallbackFactory<HttpbinClient> {

    @SuppressWarnings("Convert2Lambda")
    @Override
    public HttpbinClient create(Throwable throwable) {
        return new HttpbinClient() {

            @Override
            public HttpbinResponse get() {
                return null;
            }

            @Override
            public HttpbinResponse delay(int delay) {
                return null;
            }
        };
    }
}
