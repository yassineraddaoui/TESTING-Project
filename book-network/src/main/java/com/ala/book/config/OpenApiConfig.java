package com.ala.book.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "ala",
                        email = "bjaouiala3@gmail.com"
                ),
                description = "OpenApi documentation for Spring security",
                title = "OpenApi specification ala",
                license = @License(
                        name= "licence name",
                        url = "https://some-url.com"
                ),
                termsOfService = "terms of service"
        ),
        servers = {
                @Server(
                        description = "local ENV",
                        url = "http;//localhost:8080/api/v1"
                ),
                @Server(
                        description = "Prod ENV",
                        url = "https://domainName.com/"
                ),
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }

)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
