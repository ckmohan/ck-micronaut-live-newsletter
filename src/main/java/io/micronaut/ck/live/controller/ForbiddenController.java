package io.micronaut.ck.live.controller;

import io.micronaut.ck.live.model.Alert;
import io.micronaut.ck.live.model.AlertPage;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.ModelAndView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@Controller("/forbidden")
class ForbiddenController {

    @Operation(operationId = "forbidden",
            summary = "renders an HTML with alert about the user being authorized but lacking necessary permissions",
            description = "renders an HTML with alert about the user being authorized but lacking necessary permissions"
    )
    @ApiResponse(responseCode = "200", description = "renders an HTML with alert about the user being authorized but lacking necessary permissions")
    @Produces(MediaType.TEXT_HTML)
    @Get
    @Secured(SecurityRule.IS_AUTHENTICATED)
    ModelAndView<AlertPage> unauthorized() {
        return new ModelAndView<>("alert", createAlertPage());
    }

    private AlertPage createAlertPage() {
        String message = "Forbidden";
        return new AlertPage(message, Alert.builder()
                .danger(message)
                .build());
    }
}
