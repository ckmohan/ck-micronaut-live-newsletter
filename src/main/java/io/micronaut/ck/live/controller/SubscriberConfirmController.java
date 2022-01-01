package io.micronaut.ck.live.controller;

import io.micronaut.ck.live.data.ConfirmationService;
import io.micronaut.ck.live.model.Alert;
import io.micronaut.ck.live.services.ConfirmationCodeVerifier;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.views.View;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.thymeleaf.util.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller("/subscriber")
class SubscriberConfirmController {

    public static final String CONFIRMATION_FAILED = "Confirmation Failed";
    public static final String CONFIRMATION_SUCCESS = "Confirmation Success";
    public static final String MODEL_KEY_TITLE = "title";
    public static final String MODEL_KEY_ALERT = "alert";
    private final ConfirmationCodeVerifier confirmationCodeVerifier;
    private final ConfirmationService confirmationService;

    SubscriberConfirmController(ConfirmationCodeVerifier confirmationCodeVerifier,
                                ConfirmationService confirmationService) {
        this.confirmationCodeVerifier = confirmationCodeVerifier;
        this.confirmationService = confirmationService;
    }

    @Operation(operationId = "subscriber-confirm",
            summary = "renders HTML page with the subscription confirmation result",
            description = "renders HTML page with subscription confirmation result. It renders an alter if subscription confirmation failed or a success message if the operation could be completed"
    )
    @ApiResponse(responseCode = "200",
            description = "renders HTML page with subscription confirmation result",
            content = @Content(schema = @Schema(implementation = String.class),
                    mediaType = "text/html")
    )
    @Parameters(value = @Parameter(name = "token",
            required = true,
            in = ParameterIn.QUERY,
            example = "xxx.zzz.yyy",
            schema = @Schema(implementation = String.class),
            description = "Signed token containing the user unsubscribing in the claims"))

    @Produces(MediaType.TEXT_HTML)
    @ExecuteOn(TaskExecutors.IO)
    @View("alert")
    @Get("/confirm")
    Map<String, Object> confirm(@QueryValue @Nullable String token) {

        if (StringUtils.isEmpty(token)) {
            return createModel(CONFIRMATION_FAILED, Alert.builder()
                    .danger("token is required"));
        }
        Optional<String> email = confirmationCodeVerifier.verify(token);
        if (email.isEmpty()) {
            return createModel(CONFIRMATION_FAILED, Alert.builder()
                    .danger("could not verify the token"));

        }
        confirmationService.confirm(email.get());
        return createModel(CONFIRMATION_SUCCESS, Alert.builder()
                .success("thanks, we have now confirmed you subscription"));
    }

    private Map<String, Object> createModel(String title, Alert.Builder builder) {
        Map<String, Object> model = new HashMap<>();
        model.put(MODEL_KEY_TITLE, title);
        model.put(MODEL_KEY_ALERT, builder.build());
        return model;
    }

    private HttpResponse<?> notFound() {
        try {
            return HttpResponse.seeOther(new URI("/404"));
        } catch (URISyntaxException e) {
            return HttpResponse.serverError();
        }
    }
}
