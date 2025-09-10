package com.beour.beourminicrud.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
       return new OpenAPI()
               .info(new Info()
                       .title("beour-mini-crud API Document")
                       .version("v1.0.0")
                       .description("환영합니다! 이 API 문서는 beour-mini-crud의 API를 사용하는 방법을 설명합니다."))
               .servers(List.of(
                       new Server()
                               .url("http://localhost:8080")
                               .description("개발용 서버")
               ));
    }

/*    // 버전별로 그룹화
    @Bean
    public GroupedOpenApi groupedOpenApiV1() {
        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch("/api/v1/**")
                .build();
    }

    @Bean
    public GroupedOpenApi groupedOpenApiV2() {
        return GroupedOpenApi.builder()
                .group("v2")
                .pathsToMatch("/api/v2/**")
                .build();
    }*/
}
