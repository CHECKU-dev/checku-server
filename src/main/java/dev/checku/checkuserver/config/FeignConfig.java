package dev.checku.checkuserver.config;

import feign.Logger;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.*;

@Configuration
@EnableFeignClients(basePackages = "dev.checku.checkuserver")
@Import(FeignClientsConfiguration.class)
public class FeignConfig {


    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }



}
