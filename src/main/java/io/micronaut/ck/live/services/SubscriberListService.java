package io.micronaut.ck.live.services;

import io.micronaut.ck.live.views.SubscriberListPage;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface SubscriberListService {
    SubscriberListPage findAll(@NonNull @NotNull Integer page);
}
