package io.micronaut.ck.live.services.sendgrid;

public sealed interface SendGridConfiguration permits SendGridConfigurationProperties {
    String apiKey();
}
