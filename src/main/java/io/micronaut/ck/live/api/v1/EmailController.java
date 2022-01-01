package io.micronaut.ck.live.api.v1;

import io.micronaut.ck.live.services.EmailRequestService;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import io.micronaut.scheduling.annotation.Async;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static io.micronaut.ck.live.api.v1.Api.V1_PATH;

@Controller(V1_PATH)
public class EmailController {

    private final EmailRequestService emailRequestService;

    public EmailController(EmailRequestService emailRequestService) {
        this.emailRequestService = emailRequestService;
    }

    @Status(HttpStatus.ACCEPTED)
    @Post("/email")
    void send(@Body @NotBlank @NonNull @Valid EmailRequest emailRequest) {
        processEmailRequest(emailRequest);
    }

    @Async
    void processEmailRequest(@NonNull EmailRequest emailRequest) {
        emailRequestService.process(emailRequest);
    }
}
