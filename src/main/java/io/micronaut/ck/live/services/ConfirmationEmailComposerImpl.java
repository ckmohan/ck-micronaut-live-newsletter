package io.micronaut.ck.live.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.context.ServerRequestContext;
import io.micronaut.http.server.util.HttpHostResolver;
import io.micronaut.http.uri.UriBuilder;
import jakarta.inject.Singleton;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Singleton
public class ConfirmationEmailComposerImpl implements ConfirmationEmailComposer {
    private final ConfirmationCodeGenerator confirmationCodeGenerator;
    private final HttpHostResolver httpHostResolver;

    public ConfirmationEmailComposerImpl(ConfirmationCodeGenerator confirmationCodeGenerator, HttpHostResolver httpHostResolver) {
        this.confirmationCodeGenerator = confirmationCodeGenerator;
        this.httpHostResolver = httpHostResolver;
    }

    @Override
    @NonNull
    public String composeText(@NotNull @NotBlank String email) {
        String confirmationLink = getConfirmationLink(email);
        return String.join("\n", Arrays.asList("Thanks for subscribing", " Please, confirm your email address by clicking the following link",
                confirmationLink,
                "If you did not subscriber to the newsletter, please ignore this email"
        ));
    }

    @NonNull
    private String getConfirmationLink(@NonNull String email) {
        return UriBuilder.of(
                        ServerRequestContext.currentRequest().map(httpHostResolver::resolve)
                                .orElse(""))
                .path("confirm")
                .queryParam("token",confirmationCodeGenerator.generate(email).orElse(null))
                .build().toString();
    }
}
