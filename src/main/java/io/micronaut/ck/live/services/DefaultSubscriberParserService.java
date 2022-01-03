package io.micronaut.ck.live.services;

import io.micronaut.ck.live.Subscriber;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.util.StringUtils;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Singleton
public non-sealed class DefaultSubscriberParserService implements SubscriberParserService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultSubscriberParserService.class);
    private final Validator validator;

    public DefaultSubscriberParserService(Validator validator) {
        this.validator = validator;
    }

    @Override
    public Optional<List<Subscriber>> parseSubscribers(@NonNull @NotNull InputStream inputStream) {
        try {
            String fileContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            String[] lines = fileContent.split("\r\n");
            List<Subscriber> subscriberList = new ArrayList<>();
            Arrays.stream(lines).forEach(
                    line -> {
                        String[] arr = line.split(",");
                        Arrays.stream(arr).forEach(
                                el -> {
                                    if (el.contains("@")) {
                                        String email = StringUtils.trimLeadingWhitespace(el);
                                        Subscriber subscriber = new Subscriber(email);
                                        if (validator.validate(subscriber).isEmpty()) {
                                            subscriberList.add(subscriber);
                                        }
                                    }
                                }
                        );
                    }
            );
            return Optional.of(subscriberList);
        } catch (IOException e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("IOException reading bytes from inputStream", e);
            }
        }
        return Optional.empty();
    }
}