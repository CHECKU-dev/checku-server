package dev.checku.checkuserver.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean("asyncTask")
    public Executor threadPoolExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("asyncTask-"); // thread 이름 설정
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10); // 최대 스레드 개수
        executor.setQueueCapacity(15);
        executor.initialize();
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }

}
