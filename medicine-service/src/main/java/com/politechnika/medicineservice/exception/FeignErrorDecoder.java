package com.politechnika.medicineservice.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultError = new ErrorDecoder.Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if(response.status() == HttpStatus.NOT_FOUND.value()) {
            throw new GetPrescriberDataException("Doctor does not exist.");
        }

        return defaultError.decode(methodKey, response);
    }
}
