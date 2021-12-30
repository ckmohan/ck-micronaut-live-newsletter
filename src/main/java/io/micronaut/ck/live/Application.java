package io.micronaut.ck.live;

import io.micronaut.context.env.Environment;
import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "Newsletter subscriber",
                version = "0.1",
                description = "Newsletter subscriber API",
                license = @License(name = "Apache 2.0"),
                contact = @Contact(url = "https://gigantic-server.com", name = "ck", email = "ck@gmail.com")
        )
)
public class Application {

    public static void main(String[] args) {
        Micronaut.build(args)
                .mainClass(Application.class)
                .defaultEnvironments(Environment.DEVELOPMENT)
                .start();
    }
}
