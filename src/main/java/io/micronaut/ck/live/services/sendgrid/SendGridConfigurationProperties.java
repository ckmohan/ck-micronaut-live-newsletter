package io.micronaut.ck.live.services.sendgrid;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;

@Requires(property = SendGridConfigurationProperties.PREFIX +".api-key")
@ConfigurationProperties(SendGridConfigurationProperties.PREFIX)
public record SendGridConfigurationProperties(
        @NonNull @NotBlank String apiKey)
        implements SendGridConfiguration {
    public static final String PREFIX = "sendgrid";
}
