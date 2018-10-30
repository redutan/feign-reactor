package com.example.feignreactor.application;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @GetMapping("/status/{code}")
    HttpbinResponse status(@PathVariable("code") int code);
}

@Component
class HttpbinClientFallbackFactory implements FallbackFactory<HttpbinClient> {
    private final Logger log = LoggerFactory.getLogger(HttpbinClientFallbackFactory.class);

    @SuppressWarnings("Convert2Lambda")
    @Override
    public HttpbinClient create(Throwable cause) {
        return new HttpbinClient() {

            @Override
            public HttpbinResponse get() {
                return null;
            }

            @Override
            public HttpbinResponse delay(int delay) {
                return null;
            }

            @Override
            public HttpbinResponse status(int code) {
                log.error("Error status : {}", code, cause);
                return HttpbinResponse.EMPTY;
            }
        };
    }
}
