package io.micronaut.ck.live.conf;

import io.micronaut.core.annotation.NonNull;

public sealed interface EmailConfiguration permits EmailConfigurationProperties {
    @NonNull
    String from();
}
