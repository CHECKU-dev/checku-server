package dev.checku.checkuserver.global;

import dev.checku.checkuserver.global.exception.FeignClientException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignClientExceptionErrorDecoder implements ErrorDecoder {

    @Override
    public FeignClientException decode(final String methodKey, Response response) {
        log.info(response.toString());
        String message = response.reason();
        return new FeignClientException(response.status(), message, response.headers());
    }

}
