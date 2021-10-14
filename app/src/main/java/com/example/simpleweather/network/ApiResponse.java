package com.example.simpleweather.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ApiResponse<T> {


    @NonNull
    private final Status status;

    @Nullable
    public  T data;



    @Nullable
    private final String errorMessage;

    private ApiResponse(Status status, @Nullable T data,  @Nullable String errorMessage) {
        this.status = status;
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public static <T> ApiResponse<T> create(T response) {

        return new ApiResponse<>(Status.SUCCESS, response,  null);
    }

    public static <T> ApiResponse<T> failure(String error) {
        return new ApiResponse<>(Status.ERROR, null,  error);
    }

    public static <T> ApiResponse<T> loading() {
        return new ApiResponse<>(Status.LOADING, null,  "loading..");
    }

    @NonNull
    public Status getStatus() {
        return status;
    }

    @Nullable
    public T getData() {
        return data;
    }

    public void setData(@Nullable T data) {
        this.data = data;
    }

    @Nullable
    public String getErrorMessage() {
        return errorMessage;
    }

    public enum Status {
        SUCCESS, ERROR, LOADING
    }
}
