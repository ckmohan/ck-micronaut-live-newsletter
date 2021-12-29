package io.micronaut.ck.live.services;

import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ConfirmationEmailComposer {
   String composeText(@NonNull @NotNull String email);
}
