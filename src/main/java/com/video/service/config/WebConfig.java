package com.video.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // /api로 들어오는 모든 url 허용
                .allowedOrigins("http://localhost:8081") // 해당 url에서의 접근 허용
                .allowedMethods("GET") // 해당 REST API로의 접근 허용
                .allowedHeaders("*"); // 모든 헤더 허용

        // 위 설정들이 모두 true일 때만 접근 허용됨
    }
}
