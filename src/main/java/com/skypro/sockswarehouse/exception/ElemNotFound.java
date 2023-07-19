package com.skypro.sockswarehouse.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ElemNotFound extends RuntimeException {

    public ElemNotFound() {

    }

    public ElemNotFound(String message) {

    }
}
