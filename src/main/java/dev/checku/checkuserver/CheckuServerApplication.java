package dev.checku.checkuserver;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableEncryptableProperties
@SpringBootApplication
public class CheckuServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheckuServerApplication.class, args);
    }

}
