package com.example.feignreactor.application;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service
public class HttpbinService {
    private final HttpbinClient httpbinClient;
    private final HttpbinNoFallbackClient httpbinNoFallbackClient;
    private final FirstFailClient firstFailClient;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public HttpbinService(HttpbinClient httpbinClient,
                          HttpbinNoFallbackClient httpbinNoFallbackClient,
                          FirstFailClient firstFailClient) {
        this.httpbinClient = httpbinClient;
        this.httpbinNoFallbackClient = httpbinNoFallbackClient;
        this.firstFailClient = firstFailClient;
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

    /**
     * {@code Feign} 기반으로 호출 후 예외 발생 후 {@link HttpbinClientFallbackFactory} 기반으로 한 fallback 반환
     */
    public HttpbinResponse notFoundToFallback() {
        return httpbinClient.status(404);
    }

    /**
     * {@code Hystrix} 기반으로 호출 후 예외 발생 후 {@link #fallback()} 로 반환
     * <p>
     * 무조건 예외가 발생. 하지만 {@code @HystrixCommand} 로 {@code CircuitBreaker + Fallback} 처리
     * </p>
     */
    @HystrixCommand(commandKey = "httpbin", fallbackMethod = "fallback")
    public HttpbinResponse notFoundOnHystrix() {
        //
        return httpbinNoFallbackClient.status(404);
    }

    @SuppressWarnings({"WeakerAccess", "unused"})
    public HttpbinResponse fallback() {
        return HttpbinResponse.EMPTY;
    }

    /**
     * 무조건 예외가 발생. {@code No fallback}
     */
    public HttpbinResponse notFoundToException() {
        return httpbinNoFallbackClient.status(404);
    }

    /**
     * 최초는 실패하고 2번째에는 성공 by feign
     */
    public HttpbinResponse retryByFeign() {
        return firstFailClient.get(UUID.randomUUID().toString());
    }

}
