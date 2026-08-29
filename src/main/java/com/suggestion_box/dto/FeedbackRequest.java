package com.suggestion_box.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Request object ya kuwasilisha maoni")
public class FeedbackRequest {

    @Schema(description = "Jina kamili la mteja",
            example = "Amina Juma",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Jina kamili linahitajika")
    @Size(min = 2, max = 100, message = "Jina linapaswa kuwa kati ya 2 na 100 herufi")
    private String name;

    @Schema(description = "Barua pepe ya mteja",
            example = "amina@example.com")
    @Email(message = "Barua pepe si sahihi")
    @Size(max = 100, message = "Barua pepe haipaswi kuzidi herufi 100")
    private String email;

    @Schema(description = "Namba ya simu ya mteja",
            example = "+255712345678")
    @Pattern(regexp = "^[0-9+\\-() ]{8,20}$", message = "Namba ya simu si sahihi")
    private String phone;

    @Schema(description = "Aina ya huduma",
            example = "Uwekezaji Muda Mrefu",
            allowableValues = {"Uwekezaji Muda Mrefu", "Uwekezaji Muda Mfupi", "Ushauri wa Kifedha", "Hifadhi ya Akiba", "Mikopo ya Uwekezaji", "Nyingine"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Aina ya huduma inahitajika")
    private String serviceType;

    @Schema(description = "Rating ya huduma (1-5)",
            example = "5",
            minimum = "1",
            maximum = "5",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Rating inahitajika")
    @Min(value = 1, message = "Rating lazima iwe kati ya 1 na 5")
    @Max(value = 5, message = "Rating lazima iwe kati ya 1 na 5")
    private Integer rating;

    @Schema(description = "Maoni ya mteja",
            example = "Huduma bora sana! Nimeridhika kabisa.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Maoni yanahitajika")
    @Size(min = 5, max = 2000, message = "Maoni yanapaswa kuwa kati ya 5 na 2000 herufi")
    private String message;

    @Schema(description = "Aina ya mteja",
            example = "INDIVIDUAL",
            allowableValues = {"INDIVIDUAL", "CORPORATE", "INSTITUTIONAL"})
    private String clientType;

    @Schema(description = "Kiasi cha uwekezaji",
            example = "100K-500K")
    private String investmentAmountRange;

    @Schema(description = "Tawi au ofisi",
            example = "Dar es Salaam")
    private String branch;

    @Schema(description = "Je, maoni yanawasilishwa bila kutaja jina?",
            example = "false")
    private Boolean isAnonymous = false;

    // Constructors, getters and setters...

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        isAnonymous = anonymous;
    }

    public Boolean getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(Boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }}