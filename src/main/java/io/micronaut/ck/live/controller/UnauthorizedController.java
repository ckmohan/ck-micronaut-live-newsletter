package io.micronaut.ck.live.controller;

import io.micronaut.ck.live.model.Alert;
import io.micronaut.ck.live.model.AlertPage;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.views.ModelAndView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.security.PermitAll;


@Controller("/unauthorized")
 class UnauthorizedController {

    @Operation(operationId = "unauthorized",
            summary = "renders an HTML with alert about the user not be authorized trying to access the secure resource",
            description = "renders an HTML with alert about the user not be authorized trying to access the secure resource"
    )
    @ApiResponse(responseCode = "200", description = "renders an HTML with alert about being authorized")
    @Produces(MediaType.TEXT_HTML)
    @Get
    @PermitAll
    ModelAndView<AlertPage> unauthorized() {
        return new ModelAndView<>("alert", createAlertPage());
    }

    private AlertPage createAlertPage() {
        String message = "Unauthorized";
        return new AlertPage(message, Alert.builder()
                .danger(message)
                .build());
    }
}
