package io.micronaut.ck.live.events;

import io.micronaut.ck.live.conf.EmailConfiguration;
import io.micronaut.ck.live.model.Email;
import io.micronaut.ck.live.services.ConfirmationEmailComposer;
import io.micronaut.ck.live.services.EmailSender;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.scheduling.annotation.Async;
import jakarta.inject.Singleton;

@Requires(beans = EmailConfiguration.class)
@Singleton
public class EmailSubscriberPendingEventListener implements ApplicationEventListener<SubscriptionPendingEvent> {
    private final EmailConfiguration emailConfiguration;
    private final ConfirmationEmailComposer confirmationEmailComposer;
    private final EmailSender emailSender;

    public EmailSubscriberPendingEventListener(EmailConfiguration emailConfiguration,
                                               ConfirmationEmailComposer confirmationEmailComposer,
                                               EmailSender emailSender) {
        this.emailConfiguration = emailConfiguration;
        this.confirmationEmailComposer = confirmationEmailComposer;
        this.emailSender = emailSender;
    }

    @Override
    public void onApplicationEvent(SubscriptionPendingEvent event) {
        sendEmail(event.email());
    }

    @Async
    public void sendEmail(@NonNull String recipient) {
        String textEmail = confirmationEmailComposer.composeText(recipient);
        Email email
                = Email.EmailBuilder()
                .to(recipient)
                .subject("Confirm your subscription")
                .from(emailConfiguration.from())
                .text(textEmail)
                .build();
        emailSender.sendEmail(email);
    }
}