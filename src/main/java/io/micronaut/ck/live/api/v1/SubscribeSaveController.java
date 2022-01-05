package io.micronaut.ck.live.api.v1;

import io.micronaut.ck.live.Subscriber;
import io.micronaut.ck.live.services.SubscriberSaveService;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.security.PermitAll;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static io.micronaut.ck.live.api.v1.Api.SUBSCRIBER_PATH;
import static io.micronaut.ck.live.api.v1.Api.V1_PATH;

@Controller(V1_PATH)
class SubscribeSaveController {

    private final SubscriberSaveService subscriberSaveService;

    SubscribeSaveController(SubscriberSaveService subscriberSaveService) {
        this.subscriberSaveService = subscriberSaveService;
    }

    @Operation(operationId = "api-subscriber-save",
            summary = "Creates a subscriber pending confirmation",
            description = "Creates a subscriber pending confirmation"
    )
    @ApiResponse(responseCode = "201",
            description = "subscriber created pending confirmation")
    @ApiResponse(responseCode = "422",
            description = "subscriber already exists")
    @RequestBody(content = @Content(schema = @Schema(implementation = Subscriber.class),
            mediaType = "application/json"))
    @ExecuteOn(TaskExecutors.IO)
    @Post(SUBSCRIBER_PATH)
    @Status(HttpStatus.CREATED)
    @PermitAll
    void saveSubscriber(@Body @NotNull @NonNull @Valid Subscriber subscriber) {
        subscriberSaveService.save(subscriber);
    }
}
