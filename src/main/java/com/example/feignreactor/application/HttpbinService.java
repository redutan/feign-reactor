package com.example.feignreactor.application;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service
public class HttpbinService {
    private final HttpbinClient httpbinClient;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public HttpbinService(HttpbinClient httpbinClient) {
        this.httpbinClient = httpbinClient;
    }

    public HttpbinResponse findOne() {
        return httpbinClient.get();
    }

    public List<HttpbinResponse> find5Delay4() {
        // 병렬처리 = flatMap & defer + scheduler(immediate)
        return Flux.fromStream(IntStream.rangeClosed(1, 5).boxed())
                .flatMap(i -> monoDelay())
                .collect(toList())
                .blockOptional()
                .orElseGet(Collections::emptyList);
    }

    private Mono<HttpbinResponse> monoDelay() {
        return Mono.fromCallable(() -> httpbinClient.delay(4))
                .subscribeOn(Schedulers.elastic());
    }
}
