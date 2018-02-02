package com.kazge.example.exception;

import com.kazge.example.utils.JacksonUtils;
import org.springframework.http.HttpStatus;

import java.util.List;

public class InvalidParametersException extends ApiException {
    private List<ErrorDetail> errorDetails;

    public InvalidParametersException() {
        super(HttpStatus.BAD_REQUEST.value(), "");
    }

    public List<ErrorDetail> getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(List<ErrorDetail> errorDetails) {
        this.errorDetails = errorDetails;
    }

    @Override
    public String toString() {
        return JacksonUtils.toJsonString(getErrorDetails());
    }
}
