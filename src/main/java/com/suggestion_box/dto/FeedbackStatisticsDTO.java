package com.suggestion_box.dto;

import java.util.Map;

public class FeedbackStatisticsDTO {

    private Long totalFeedbacks;
    private Double averageRating;
    private Integer minRating;
    private Integer maxRating;
    private Map<String, Long> statusCounts;
    private Map<Integer, Long> ratingCounts;
    private Map<String, Double> averageRatingByService;

    // ============================================
    // CONSTRUCTORS
    // ============================================
    public FeedbackStatisticsDTO() {
    }

    public FeedbackStatisticsDTO(Long totalFeedbacks, Double averageRating, Integer minRating,
                                 Integer maxRating, Map<String, Long> statusCounts,
                                 Map<Integer, Long> ratingCounts,
                                 Map<String, Double> averageRatingByService) {
        this.totalFeedbacks = totalFeedbacks;
        this.averageRating = averageRating;
        this.minRating = minRating;
        this.maxRating = maxRating;
        this.statusCounts = statusCounts;
        this.ratingCounts = ratingCounts;
        this.averageRatingByService = averageRatingByService;
    }

    // ============================================
    // GETTERS AND SETTERS
    // ============================================
    public Long getTotalFeedbacks() {
        return totalFeedbacks;
    }

    public void setTotalFeedbacks(Long totalFeedbacks) {
        this.totalFeedbacks = totalFeedbacks;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getMinRating() {
        return minRating;
    }

    public void setMinRating(Integer minRating) {
        this.minRating = minRating;
    }

    public Integer getMaxRating() {
        return maxRating;
    }

    public void setMaxRating(Integer maxRating) {
        this.maxRating = maxRating;
    }

    public Map<String, Long> getStatusCounts() {
        return statusCounts;
    }

    public void setStatusCounts(Map<String, Long> statusCounts) {
        this.statusCounts = statusCounts;
    }

    public Map<Integer, Long> getRatingCounts() {
        return ratingCounts;
    }

    public void setRatingCounts(Map<Integer, Long> ratingCounts) {
        this.ratingCounts = ratingCounts;
    }

    public Map<String, Double> getAverageRatingByService() {
        return averageRatingByService;
    }

    public void setAverageRatingByService(Map<String, Double> averageRatingByService) {
        this.averageRatingByService = averageRatingByService;
    }
}