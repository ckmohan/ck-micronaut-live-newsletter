package io.micronaut.ck.live.api.v1;

import io.micronaut.ck.live.data.SubscriberService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.inject.Inject;

import static io.micronaut.ck.live.api.v1.Api.SUBSCRIBER_PATH;
import static io.micronaut.ck.live.api.v1.Api.V1_PATH;

@Controller(V1_PATH)
class SubscriberCountController {

    @Inject
    private final SubscriberService subscriberService;

    SubscriberCountController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @ExecuteOn(TaskExecutors.IO)
    @Get(SUBSCRIBER_PATH + "/count")
    @Produces(MediaType.TEXT_PLAIN)
    Integer subscriberCount() {
        return subscriberService.getConfirmedSubscribersCount();
    }
}
