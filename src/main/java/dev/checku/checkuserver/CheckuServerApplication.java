package dev.checku.checkuserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

//@EnableRetry
@EnableRetry(proxyTargetClass=true)
@SpringBootApplication
public class CheckuServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheckuServerApplication.class, args);
    }

}
