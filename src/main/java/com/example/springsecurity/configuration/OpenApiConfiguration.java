package com.example.springsecurity.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
public class OpenApiConfiguration {
  static final String SECURITY_SCHEME_NAME = "Bearer oAuth Token";
  @Bean
  public OpenAPI openApi(
      @Value("${openapi.title}") final String title,
      @Value("${openapi.version}") final String version,
      @Value("${openapi.description}") final String description
  ) {
    return new OpenAPI()
        .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
        .components(
            new Components()
                .addSecuritySchemes(SECURITY_SCHEME_NAME,
                    new SecurityScheme()
                        .name(SECURITY_SCHEME_NAME)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
        )
        .info(new Info()
            .title(title)
            .version(version)
            .description(description)
            .termsOfService("Terms of service")
            .license(getLicense())
            .contact(getContact())
        );


  }
   private Contact getContact(){
    Contact contact = new Contact();
    contact.setEmail("huot.nich8888@gmail.com");
    contact.setName("Chansreynich Huot");
    contact.setUrl("https://medium.com/chansreynich");
    contact.setExtensions(Collections.emptyMap());
    return contact;
  }

    private License getLicense() {
    License license = new License();
    license.setName("Apache License, Version 2.0");
    license.setUrl("http://www.apache.org/licenses/LICENSE-2.0");
    license.setExtensions(Collections.emptyMap());
    return license;
  }

}
