package org.example.testkra.xml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public class ApiResponse<T> {
    private boolean success;
    private Status status;
    private String details;
    private T data;
    private Pagination pagination;
    private ApiError error;

    // Constructors
    public ApiResponse() {
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Status{
        private int code;
        private HttpStatus message;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Pagination{
     private int total;
     private int count;
     private int per_page;
     private int current_page;
     private int total_pages;

    }

    public ApiResponse(boolean success, Status status, String details, T data, Pagination pagination, ApiError error) {
        this.success = success;
        this.status = status;
        this.details = details;
        this.data = data;
        this.pagination = pagination;
        this.error = error;
    }

    // Static factory methods for ease of use
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, new Status(HttpStatus.OK.value(), HttpStatus.OK), "Request was successful", data, null, null);
    }
    public static <T> ApiResponse<T> successPassCustomMessage(String message, T data) {
        return new ApiResponse<>(true, new Status(HttpStatus.OK.value(), HttpStatus.OK), message, data, null, null);
    }

    public static <T> ApiResponse<T> successPassCustomMessageAndStatus(HttpStatus status, String message, T data) {
        return new ApiResponse<>(true, new Status(status.value(), status), message, data, null, null);
    }

    public static <T> ApiResponse<T> successPassCustomMessageStatusAndCode(HttpStatus status, int code, String message, T data) {
        return new ApiResponse<>(true, new Status(code, status), message, data, null, null);
    }
    public static <T> ApiResponse<T> error(String details, ApiError error) {
        return new ApiResponse<>(false, new Status(HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED), details, null, null, error);
    }
    public static <T> ApiResponse<T> errorPassStatus(String details, HttpStatus status) {

        return new ApiResponse<>(false,new Status(status.value(), status), details, null, null, new ApiError(status, details, status.value()));
    }
    public static <T> ApiResponse<T> errorPassStatusAndCode(String details, HttpStatus status, int errorCode) {
        return new ApiResponse<>(false, new Status(errorCode, status), details, null, null, new ApiError(status, details, errorCode));
    }
}