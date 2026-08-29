package com.suggestion_box.dto;

import java.time.LocalDateTime;

public class FeedbackResponse {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String serviceType;
    private Integer rating;
    private String message;
    private String status;
    private String responseMessage;
    private LocalDateTime submittedAt;
    private LocalDateTime updatedAt;
    private String clientType;
    private String investmentAmountRange;
    private String branch;
    private Boolean isAnonymous;

    // ============================================
    // CONSTRUCTORS
    // ============================================
    public FeedbackResponse() {
    }

    public FeedbackResponse(Long id, String fullName, String email, String phone,
                            String serviceType, Integer rating, String message,
                            String status, String responseMessage,
                            LocalDateTime submittedAt, LocalDateTime updatedAt,
                            String clientType, String investmentAmountRange,
                            String branch, Boolean isAnonymous) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.serviceType = serviceType;
        this.rating = rating;
        this.message = message;
        this.status = status;
        this.responseMessage = responseMessage;
        this.submittedAt = submittedAt;
        this.updatedAt = updatedAt;
        this.clientType = clientType;
        this.investmentAmountRange = investmentAmountRange;
        this.branch = branch;
        this.isAnonymous = isAnonymous;
    }

    // ============================================
    // GETTERS AND SETTERS
    // ============================================
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getInvestmentAmountRange() {
        return investmentAmountRange;
    }

    public void setInvestmentAmountRange(String investmentAmountRange) {
        this.investmentAmountRange = investmentAmountRange;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Boolean getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(Boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }
}