package dev.checku.checkuserver.global.config;

import dev.checku.checkuserver.global.interceptor.LogTraceIdInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LogTraceIdInterceptor logTraceIdInterceptor;

    public WebConfig(LogTraceIdInterceptor logTraceIdInterceptor) {
        this.logTraceIdInterceptor = logTraceIdInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logTraceIdInterceptor)
                .addPathPatterns("/**");
    }
}
