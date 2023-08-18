package com.prosigmaka.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseEnvelope {

    final private int status;
    final private String message;
    final private Object result;

}
