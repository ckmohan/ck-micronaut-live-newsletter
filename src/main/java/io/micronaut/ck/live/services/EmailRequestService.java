package io.micronaut.ck.live.services;

import io.micronaut.ck.live.api.v1.EmailRequest;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface EmailRequestService {
    void process(@NonNull @NotNull HttpRequest<?> httpRequest,
                 @NonNull @NotBlank @Valid EmailRequest emailRequest);
}
