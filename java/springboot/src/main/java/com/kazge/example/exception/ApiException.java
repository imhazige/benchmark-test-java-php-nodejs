package com.kazge.example.exception;


import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    private int status = HttpStatus.BAD_REQUEST.value();
    private String message;

    public ApiException(int status,String message){
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
