package io.micronaut.ck.live.services;

import io.micronaut.ck.live.model.Email;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Singleton
public class DevEmailSender implements EmailSender {
    private static final Logger LOG = LoggerFactory.getLogger(DevEmailSender.class);

    @Override
    public void sendEmail(@NonNull @NotBlank @Valid Email email) {
        if (LOG.isInfoEnabled()) {
            LOG.info("{}", email);
        }
    }
}
