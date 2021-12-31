package io.micronaut.ck.live.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Introspected
public record Email(
        @NonNull @NotBlank @javax.validation.constraints.Email String to,
        @NonNull @NotBlank @javax.validation.constraints.Email String from,
        @Nullable String html,
        @Nullable String text,
        @Nullable List<@javax.validation.constraints.Email String> cc,
        @Nullable List<@javax.validation.constraints.Email String> bcc,
        @NonNull @NotBlank String subject) {

    public static Builder EmailBuilder() {
        return new Builder();
    }

    private Email(Builder builder) {
        this(builder.to, builder.from, builder.html, builder.text, builder.cc
                , builder.bcc, builder.subject);
    }
    public static final class Builder {

        private @NonNull @NotBlank @javax.validation.constraints.Email String to;
        private @NonNull @NotBlank @javax.validation.constraints.Email String from;
        private @Nullable String html;
        private @Nullable String text;
        private @Nullable List<@javax.validation.constraints.Email String> cc = new ArrayList<>();
        private @Nullable List<@javax.validation.constraints.Email String> bcc = new ArrayList<>();
        private @NonNull @NotBlank String subject;

        private Builder() {
            // Use static EmailBuilder() method
        }

        public Builder to(@NonNull @NotBlank @javax.validation.constraints.Email String to) {
            this.to = to;
            return this;
        }

        public Builder from(@NonNull @NotBlank @javax.validation.constraints.Email String from) {
            this.from = from;
            return this;
        }

        public Builder html(@Nullable String html) {
            this.html = html;
            return this;
        }

        public Builder text(@Nullable String text) {
            this.text = text;
            return this;
        }

        public Builder cc(@Nullable String zcc) {
            if (Objects.isNull(zcc)){
                return this;
            }
            this.cc.add(zcc);
            return this;
        }

        public Builder bcc(@Nullable String zbcc) {
            if (Objects.isNull(zbcc)){
                return this;
            }
            this.bcc.add(zbcc);
            return this;
        }

        public Builder subject(@NonNull @NotBlank String subject) {
            this.subject = subject;
            return this;
        }

        public Email build() {
            return new Email(this);
        }
    }
}

