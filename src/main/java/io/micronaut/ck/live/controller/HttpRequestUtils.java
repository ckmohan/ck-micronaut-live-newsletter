package io.micronaut.ck.live.controller;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;

public final class HttpRequestUtils {
    public static boolean acceptHtml(HttpRequest<?> request) {
        return request.getHeaders()
                .accept()
                .stream()
                .anyMatch(mediaType -> mediaType.getName().contains(MediaType.TEXT_HTML));
    }
}
