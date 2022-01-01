package io.micronaut.ck.live.services;

import io.micronaut.ck.live.Subscriber;
import io.micronaut.ck.live.api.v1.EmailRequest;
import io.micronaut.ck.live.conf.EmailConfiguration;
import io.micronaut.ck.live.data.SubscriberService;
import io.micronaut.ck.live.model.Email;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Singleton
public class EmailRequestServiceImpl implements EmailRequestService {
    private final SubscriberService subscriberService;
    private final EmailConfiguration emailConfiguration;
    private final EmailSender emailSender;

    public EmailRequestServiceImpl(SubscriberService subscriberService, EmailConfiguration emailConfiguration, EmailSender emailSender) {
        this.subscriberService = subscriberService;
        this.emailConfiguration = emailConfiguration;
        this.emailSender = emailSender;
    }


    @Override
    public void process(@NonNull @NotBlank @Valid EmailRequest emailRequest) {

        subscriberService.findAll().forEach(subscriber -> {
            Email email = compose(subscriber, emailRequest);
            emailSender.sendEmail(email);
        });

    }

    @NonNull
    private Email compose(@NonNull Subscriber subscriber, @NonNull EmailRequest emailRequest) {
        return Email.EmailBuilder()
                .from(emailConfiguration.from())
                .subject(emailRequest.subject())
                .text(emailRequest.text())
                .html(emailRequest.html())
                .to(subscriber.email())
                .build();

    }
}
