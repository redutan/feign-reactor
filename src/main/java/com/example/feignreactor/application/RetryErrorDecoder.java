package com.example.feignreactor.application;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;

@SuppressWarnings("unused")
public class RetryErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        RestClientException cause = new RestClientException(response.toString());
        final int status = response.status();
        if (HttpStatus.valueOf(status).is5xxServerError()) {
            return new RetryableException(methodKey, cause, null);
        }
        return cause;
    }
}
