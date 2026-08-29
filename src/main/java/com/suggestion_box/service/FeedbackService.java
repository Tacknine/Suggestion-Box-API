package com.suggestion_box.service;

import com.suggestion_box.dto.FeedbackRequest;
import com.suggestion_box.dto.FeedbackResponse;
import com.suggestion_box.dto.FeedbackStatisticsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface FeedbackService {

    // Submit new feedback
    FeedbackResponse submitFeedback(FeedbackRequest request, String ipAddress, String userAgent);

    // Get all feedbacks with pagination
    Page<FeedbackResponse> getAllFeedbacks(Pageable pageable);

    // Get feedback by ID
    FeedbackResponse getFeedbackById(Long id);

    // Update feedback status
    FeedbackResponse updateStatus(Long id, String status, String responseMessage, Long respondedBy);

    // Get feedbacks by status
    List<FeedbackResponse> getFeedbacksByStatus(String status);

    // Get feedbacks by service type
    List<FeedbackResponse> getFeedbacksByServiceType(String serviceType);

    // Get feedbacks by date range
    List<FeedbackResponse> getFeedbacksByDateRange(LocalDateTime start, LocalDateTime end);

    // Get feedbacks by rating
    List<FeedbackResponse> getFeedbacksByRating(Integer rating);

    // Search feedbacks by keyword
    List<FeedbackResponse> searchFeedbacks(String keyword);

    // Get statistics
    FeedbackStatisticsDTO getStatistics();

    // Delete feedback
    void deleteFeedback(Long id);

    // Get recent feedbacks
    Page<FeedbackResponse> getRecentFeedbacks(Pageable pageable);
}