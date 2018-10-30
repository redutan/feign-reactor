package com.example.feignreactor.application;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HttpbinResponse {
    static final HttpbinResponse EMPTY = new HttpbinResponse("", "");
    private final String origin;
    private final String url;

    @JsonCreator
    public HttpbinResponse(@JsonProperty("origin") String origin,
                           @JsonProperty("url") String url) {
        this.origin = origin;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getOrigin() {
        return origin;
    }

    @Override
    public String toString() {
        return "HttpbinResponse{" +
                "origin='" + origin + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
