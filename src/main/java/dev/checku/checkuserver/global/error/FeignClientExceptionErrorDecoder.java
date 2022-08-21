package dev.checku.checkuserver.global.error;

import dev.checku.checkuserver.global.error.exception.FeignClientException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class FeignClientExceptionErrorDecoder implements ErrorDecoder {

    @Override
    public FeignClientException decode(final String methodKey, Response response) {
        System.out.println("====================================");
        System.out.println(response.body().toString());
        log.error(response.toString());
//        if (response.toString().contains(""))
//        RetryableException
        String message = response.reason();
        return new FeignClientException(response.status(), message, response.headers());
    }

}
