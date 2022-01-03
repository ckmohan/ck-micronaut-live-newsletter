package io.micronaut.ck.live.controller;

import io.micronaut.ck.live.model.Alert;
import io.micronaut.ck.live.model.AlertPage;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.hateoas.JsonError;
import io.micronaut.http.hateoas.Link;
import io.micronaut.views.ModelAndView;
import io.micronaut.views.ViewsRenderer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Controller("/404")
class NotFoundController {

    private final ViewsRenderer<AlertPage> viewsRenderer;

    NotFoundController(ViewsRenderer<AlertPage> viewsRenderer) {
        this.viewsRenderer = viewsRenderer;
    }


    @Operation(operationId = "notfound",
            summary = "renders an HTML with alert about a page",
            description = "renders an HTML with alert about a page"
    )
    @ApiResponse(responseCode = "202", description = "renders an HTML with alert about a page not found")

    @Produces(MediaType.TEXT_HTML)
    @Get
    ModelAndView<AlertPage> notFound() {
        return new ModelAndView<>("alert", createAlertPage());
    }

    private AlertPage createAlertPage() {
        return new AlertPage("Not Found", Alert.builder()
                .danger("Not Found")
                .build());
    }

    @Error(status = HttpStatus.NOT_FOUND, global = true)
    public HttpResponse<?> notFound(HttpRequest<?> request) {
        if (HttpRequestUtils.acceptHtml(request)) {
            return HttpResponse.ok(viewsRenderer.render("notFound", createAlertPage(), request))
                    .contentType(MediaType.TEXT_HTML);
        }
        JsonError error = new JsonError("Page Not Found")
                .link(Link.SELF, Link.of(request.getUri()));

        return HttpResponse.<JsonError>notFound()
                .body(error);
    }

}
