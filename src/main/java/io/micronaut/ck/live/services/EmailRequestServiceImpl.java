package io.micronaut.ck.live.services;

import io.micronaut.ck.live.Subscriber;
import io.micronaut.ck.live.api.v1.EmailRequest;
import io.micronaut.ck.live.conf.EmailConfiguration;
import io.micronaut.ck.live.data.SubscriberService;
import io.micronaut.ck.live.model.Email;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.server.util.HttpHostResolver;
import io.micronaut.http.uri.UriBuilder;
import jakarta.inject.Singleton;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Singleton
public class EmailRequestServiceImpl implements EmailRequestService {
    public static final String UNSUBSCRIBE = "@UNSUBSCRIBE@";
    private final SubscriberService subscriberService;
    private final EmailConfiguration emailConfiguration;
    private final EmailSender emailSender;
    private final HttpHostResolver httpHostResolver;
    private final ConfirmationCodeGenerator confirmationCodeGenerator;

    public EmailRequestServiceImpl(SubscriberService subscriberService, EmailConfiguration emailConfiguration, EmailSender emailSender, HttpHostResolver httpHostResolver, ConfirmationCodeGenerator confirmationCodeGenerator) {
        this.subscriberService = subscriberService;
        this.emailConfiguration = emailConfiguration;
        this.emailSender = emailSender;
        this.httpHostResolver = httpHostResolver;
        this.confirmationCodeGenerator = confirmationCodeGenerator;
    }


    @Override
    public void process(@NonNull @NotNull HttpRequest<?> httpRequest,
                        @NonNull @NotNull @Valid EmailRequest emailRequest) {

        subscriberService.findAll().forEach(subscriber -> {
            Email email = compose(httpRequest, subscriber, emailRequest);
            emailSender.sendEmail(email);
        });

    }

    private Optional<String> unsubscribeLink(@NonNull HttpRequest<?> httpRequest,
                                            @NonNull Subscriber subscriber) {

        return confirmationCodeGenerator.generate(subscriber.email()).map(token -> {
            String host = httpHostResolver.resolve(httpRequest);
            return UriBuilder
                    .of(host).path("/unsubscribe")
                    .queryParam("token", token)
                    .build().toString();
        });
    }

    @NonNull
    private Email compose(@NonNull HttpRequest<?> httpRequest,
                          @NonNull Subscriber subscriber,
                          @NonNull EmailRequest emailRequest) {
        String text = emailRequest.text();
        String html = emailRequest.html();
        if (text != null || html != null) {
            Optional<String> unSubscribeLinkOptional = unsubscribeLink(httpRequest, subscriber);
            if (unSubscribeLinkOptional.isPresent()) {
                String unSubscribeLink = unSubscribeLinkOptional.get();
                if (text != null) {
                    text = text.replace(UNSUBSCRIBE, unSubscribeLink);
                }
                if (html != null) {
                    html = html.replace(UNSUBSCRIBE, unSubscribeLink);
                }

            }

        }
        return Email.EmailBuilder()
                .from(emailConfiguration.from())
                .subject(emailRequest.subject())
                .text(text)
                .html(html)
                .to(subscriber.email())
                .build();

    }
}
