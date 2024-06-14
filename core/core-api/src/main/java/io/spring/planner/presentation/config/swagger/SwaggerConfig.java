package io.spring.planner.presentation.config.swagger;

import io.spring.planner.authorization.AuthorizationMetaData;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
public class SwaggerConfig {
    private static final String SECURITY_SCHEME_KEY = "JWT_SECURITY_KEY";

    static {
        SpringDocUtils.getConfig()
            .replaceWithClass(LocalDateTime.class, String.class)
            .replaceWithClass(LocalDate.class, String.class)
            .replaceWithClass(LocalTime.class, String.class);
    }


    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(apiInfo())
            .components(createComponents())
            .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_KEY))
            ;
    }

    private Info apiInfo() {
        return new Info()
            .title("TODO 미니 프로젝트 API")
            .description("API 목록")
            .version("1.0.0")
            ;
    }

    private Components createComponents() {
        return new Components()
            .addSecuritySchemes(
                SECURITY_SCHEME_KEY,
                new SecurityScheme()
                    .type(SecurityScheme.Type.APIKEY)
                    .in(SecurityScheme.In.HEADER)
                    .name(AuthorizationMetaData.ACCESS_TOKEN.name())
            );
    }
}
