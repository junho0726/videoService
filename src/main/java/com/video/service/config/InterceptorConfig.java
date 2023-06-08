package com.video.service.config;


import com.video.service.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = {"com.video.service.interceptor"})
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    JwtInterceptor jwtInterceptor;

    private static final String[] EXCLUDE_PATHS = {
            "/api/user/login",
            "/api/user/joinProc",
            "/api/user/checkId",
            "/api/video/findAll",
            "/api/video/findDetail/**",
            "/api/category/findAll",
            "/api/comment/**",
            "/api/channel/**",
            "/api/saveVideo/**",
            "/api/playList/**"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(EXCLUDE_PATHS);
    }
}
