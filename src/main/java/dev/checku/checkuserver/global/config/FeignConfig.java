package dev.checku.checkuserver.global.config;

import dev.checku.checkuserver.global.error.FeignClientExceptionErrorDecoder;
import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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

    @Bean
    @ConditionalOnMissingBean(value = ErrorDecoder.class)
    public FeignClientExceptionErrorDecoder commonFeignErrorDecoder() {
        return new FeignClientExceptionErrorDecoder();
    }

    //TODO 고민
//    @Bean
//    public Retryer retryer() {
//        // 재시도는 1초를 시작으로 최대 2초로 재시도 하고, 최대 3번으로 재시도 하도록 설정
//        // 최초 1초이고, 그 이후는 1.5를 곱하면서 재시도
//        return new Retryer.Default(1000, 2000, 3);
//    }



}
