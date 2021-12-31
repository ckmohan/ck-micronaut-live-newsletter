package io.micronaut.ck.live.api.v1;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import io.micronaut.http.client.annotation.Client;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static io.micronaut.ck.live.api.v1.Api.V1_PATH;

@Controller(V1_PATH)
public class EmailController {

    @Status(HttpStatus.ACCEPTED)
    @Post("/email")
    void send(@Body @NotBlank @NonNull @Valid EmailRequest emailRequest){

    }
}
