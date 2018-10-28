package com.example.feignreactor.application;


public class HttpbinResponse {
    private String origin;
    private String url;

    public HttpbinResponse() {
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "HttpbinResponse{" +
                "origin='" + origin + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
