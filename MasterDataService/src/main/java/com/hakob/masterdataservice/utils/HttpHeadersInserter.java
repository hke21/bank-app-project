package com.hakob.masterdataservice.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HttpHeadersInserter {
    public HttpHeaders insertResponseTraceHeaders(Map<String, String> requestHeaders) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("x-b3-traceid", requestHeaders.get("x-b3-traceid"));
        responseHeaders.set("x-b3-spanid", requestHeaders.get("x-b3-spanid"));
        responseHeaders.set("x-b3-parentspanid", requestHeaders.get("x-b3-parentspanid"));
        responseHeaders.set("x-b3-sampled", requestHeaders.get("x-b3-sampled"));
        responseHeaders.set("x-b3-flags", requestHeaders.get("x-b3-flags"));
        responseHeaders.set("x-request-id", requestHeaders.get("x-request-id"));

        return responseHeaders;
    }

}
