package com.indraparkapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ApiException extends Exception {

    public ApiException(String msg) {
        super(msg);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    static class ModelNotFoundException extends ApiException {
        ModelNotFoundException(String msg){
            super(msg);
        }
    }

    //TODO Add Test
    public static ApiException modelNotFound() {
        return new ModelNotFoundException("Model Not Found Exception");
    }
}
