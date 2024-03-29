package io.micronaut.ck.live.data;

import io.micronaut.ck.live.Subscriber;
import io.micronaut.ck.live.model.SubscriptionStatus;
import io.micronaut.ck.live.views.SubscriberRow;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface SubscriberDataRepository extends CrudRepository<SubscriberEntity, String> {

    Integer countByStatus(SubscriptionStatus subscriptionStatus);

    @Query("UPDATE subscriber SET status = :status WHERE  email = :email")
    void updateStatusByEmail(@NonNull @NotNull SubscriptionStatus status,
                             @NonNull @NotNull String email);

    @NonNull
    List<Subscriber> findByStatus(@NonNull @NotNull SubscriptionStatus subscriptionStatus);

    long countByEmail(@NonNull @NotNull @Email String email);

    @NonNull
    List<SubscriberRow> find(Pageable pageable);
}
