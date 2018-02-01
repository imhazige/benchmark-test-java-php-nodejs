package com.kazge.example.exception;

import com.kazge.example.utils.JacksonUtils;

import java.util.List;

public class InvalidParametersException extends ApiException {
    private List<ErrorDetail> errorDetails;

    public InvalidParametersException(int status, String message) {
        super(status, message);
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
