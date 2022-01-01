package io.micronaut.ck.live.services;

import io.micronaut.ck.live.api.v1.EmailRequest;
import io.micronaut.core.annotation.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

public  interface EmailRequestService {
    void process(@NonNull @NotBlank @Valid EmailRequest emailRequest);
}
