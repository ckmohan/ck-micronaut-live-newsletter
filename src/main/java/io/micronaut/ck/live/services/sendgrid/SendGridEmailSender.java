package io.micronaut.ck.live.services.sendgrid;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Personalization;
import io.micronaut.ck.live.model.Email;
import io.micronaut.ck.live.services.EmailSender;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@Requires(beans = SendGridConfiguration.class)
@Singleton
public class SendGridEmailSender implements EmailSender {
    private static final Logger LOG = LoggerFactory.getLogger(SendGridEmailSender.class);
    private final SendGrid sendGrid;

    public SendGridEmailSender(SendGridConfiguration sendGridConfiguration) {
        sendGrid = new SendGrid(sendGridConfiguration.apiKey());
    }

    @Override
    public void sendEmail(@NonNull @NotBlank @Valid Email email) {
        try {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Sending email to {}", email.to());
            }
            send(createRequest(createEmail(email)));
        } catch (IOException ex) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Error Sending email", ex);
            }
        }
    }

    @NonNull
    private Mail createEmail(@NonNull Email email) {
        Mail mail = new Mail();
        com.sendgrid.helpers.mail.objects.Email from = new com.sendgrid.helpers.mail.objects.Email();
        from.setEmail(email.from());
        mail.from = from;
        mail.addPersonalization(createPersonalization(email));
        contentOfEmail(email).ifPresent(mail::addContent);
        return mail;
    }

    @NonNull
    private Personalization createPersonalization(@NonNull Email email) {
        Personalization personalization = new Personalization();
        personalization.setSubject(email.subject());

        com.sendgrid.helpers.mail.objects.Email to = new com.sendgrid.helpers.mail.objects.Email(email.to());
        personalization.addTo(to);

        if (email.cc() != null) {
            for (String cc : email.cc()) {
                com.sendgrid.helpers.mail.objects.Email ccEmail = new com.sendgrid.helpers.mail.objects.Email();
                ccEmail.setEmail(cc);
                personalization.addCc(ccEmail);
            }
        }

        if (email.bcc() != null) {
            for (String bcc : email.bcc()) {
                com.sendgrid.helpers.mail.objects.Email bccEmail = new com.sendgrid.helpers.mail.objects.Email();
                bccEmail.setEmail(bcc);
                personalization.addBcc(bccEmail);
            }
        }
        return personalization;
    }

    @NonNull
    private Request createRequest(@NonNull Mail mail) throws IOException {
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        return request;
    }

    private void send(@NonNull Request request) throws IOException {
        Response response = sendGrid.api(request);
        if (LOG.isInfoEnabled()) {
            LOG.info("Status Code: {}", response.getStatusCode());
            LOG.info("Body: {}", response.getBody());
            LOG.info("Headers {}", response.getHeaders()
                    .keySet()
                    .stream()
                    .map(key -> key + "=" + response.getHeaders().get(key))
                    .collect(Collectors.joining(", ", "{", "}")));
        }
    }

    private Optional<Content> contentOfEmail(Email email) {

        if (email.text() != null) {
            return Optional.of(new Content("text/plain", email.text()));
        }
        if (email.html() != null) {
            return Optional.of(new Content("text/html", email.html()));
        }
        return Optional.empty();
    }
}
