package io.micronaut.ck.live.conf;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;

@Requires(property = EmailConfigurationProperties.PREFIX+".from")
@ConfigurationProperties(EmailConfigurationProperties.PREFIX)
public record EmailConfigurationProperties(@NonNull String from)
        implements EmailConfiguration {
    public static final String PREFIX = "email";
}
