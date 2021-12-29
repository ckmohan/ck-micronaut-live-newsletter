package io.micronaut.ck.live.events;

import io.micronaut.ck.live.model.Email;
import io.micronaut.ck.live.services.ConfirmationEmailComposer;
import io.micronaut.ck.live.services.EmailSender;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.scheduling.annotation.Async;
import jakarta.inject.Singleton;

@Singleton
public class EmailSubscriberPendingEventListener implements ApplicationEventListener<SubscriptionPendingEvent> {
    private final ConfirmationEmailComposer confirmationEmailComposer;
    private final EmailSender emailSender;

    public EmailSubscriberPendingEventListener(ConfirmationEmailComposer confirmationEmailComposer, EmailSender emailSender) {
        this.confirmationEmailComposer = confirmationEmailComposer;
        this.emailSender = emailSender;
    }

    @Override
    public void onApplicationEvent(SubscriptionPendingEvent event) {
        sendEmail(event.email());
    }

    @Async
    public void sendEmail(@NonNull String recipent) {
        String textEmail = confirmationEmailComposer.composeText(recipent);
        Email email = new Email(recipent, null, textEmail);
        emailSender.sendEmail(email);
    }
}