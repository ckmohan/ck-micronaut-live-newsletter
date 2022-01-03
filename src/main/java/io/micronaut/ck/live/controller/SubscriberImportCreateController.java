package io.micronaut.ck.live.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.views.View;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.Collections;
import java.util.Map;

@Controller("/subscriber")
class SubscriberImportCreateController {
    @Operation(operationId = "subscriber-import-create",
            summary = "renders HTML page with a form to submit a CSV file",
            description = "renders HTML page with a form to submit a CSV file"
    )
    @ApiResponse(responseCode = "200",
            description = "renders HTML page with a form to submit a CSV file",
            content = @Content(schema = @Schema(implementation = String.class),
                    mediaType = "text/html")
    )
    @Produces(MediaType.TEXT_HTML)
    @Get("/import")
    @View("subscriberimport")
    Map<String, Object> create() {
        return Collections.emptyMap();
    }

}
