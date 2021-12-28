package io.micronaut.ck.live.services;

import java.util.Optional;

@FunctionalInterface
public interface IdGenerator {
    Optional<String> generate();
}
