package com.example.SHORT.TERM.TRADING.APP.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class ReactRoutingConfig implements WebFluxConfigurer {

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route(RequestPredicates.GET("/**")
                        .and(RequestPredicates.path("/entry/api/**").negate())
                        .and(RequestPredicates.path("/token/api/**").negate())
                        .and(RequestPredicates.path("/test").negate())
                        .and(RequestPredicates.path("/test/**").negate())
                        .and(RequestPredicates.path("/static/**").negate()),
                request -> ServerResponse.ok().contentType(MediaType.TEXT_HTML).bodyValue(indexHtml()));
    }

    private String indexHtml() {
        try {
            Resource resource = new ClassPathResource("/static/index.html");
            InputStream inputStream = resource.getInputStream();
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            return new String(bdata, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading index.html", e);
        }
    }

}