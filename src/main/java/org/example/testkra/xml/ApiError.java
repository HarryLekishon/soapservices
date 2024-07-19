package org.example.testkra.xml;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiError {

    private int errorCode;
    private HttpStatus message;
    private String details;

    //    private String details;

    // Constructors
    public ApiError() {
    }

    public ApiError(String details) {
        this.details = details;
    }

    public ApiError(HttpStatus httpStatus, String details, int errorCode) {
        this.message = httpStatus;
        this.details = details;
        this.errorCode = errorCode;
    }
}