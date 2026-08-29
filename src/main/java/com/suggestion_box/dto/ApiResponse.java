package com.suggestion_box.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Standard API response wrapper")
public class ApiResponse<T> {

    @Schema(description = "Je, operesheni imefanikiwa?", example = "true")
    private boolean success;

    @Schema(description = "Ujumbe wa operesheni", example = "Maoni yamewasilishwa kikamilifu")
    private String message;

    @Schema(description = "Data ya operesheni")
    private T data;

    @Schema(description = "Muda wa operesheni", example = "2026-08-28T21:13:05.398994600")
    private String timestamp;

    // ============================================
    // CONSTRUCTORS
    // ============================================
    public ApiResponse() {
    }

    public ApiResponse(boolean success, String message, T data, String timestamp) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    // ============================================
    // STATIC FACTORY METHODS - HIZI NDIZO ZINAHITAJIKA!
    // ============================================
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, LocalDateTime.now().toString());
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null, LocalDateTime.now().toString());
    }

    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(false, message, data, LocalDateTime.now().toString());
    }

    // ============================================
    // GETTERS AND SETTERS
    // ============================================
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}