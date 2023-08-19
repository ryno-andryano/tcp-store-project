package com.prosigmaka.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ResponseEnvelope {

    private int status;
    private String message;
    private Object result;

    public ResponseEnvelope(HttpStatus httpStatus, Object result) {
        this.status = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
        this.result = result;
    }
}
