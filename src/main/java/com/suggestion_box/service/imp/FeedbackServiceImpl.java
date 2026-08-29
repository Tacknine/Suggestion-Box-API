package com.suggestion_box.service.imp;

import com.suggestion_box.dto.FeedbackRequest;
import com.suggestion_box.dto.FeedbackResponse;
import com.suggestion_box.dto.FeedbackStatisticsDTO;
import com.suggestion_box.entity.Feedback;
import com.suggestion_box.repository.FeedbackRepository;
import com.suggestion_box.service.FeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private static final Logger log = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    private final FeedbackRepository feedbackRepository;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    @Transactional
    public FeedbackResponse submitFeedback(FeedbackRequest request, String ipAddress, String userAgent) {
        Feedback feedback = new Feedback();

        // USING name INSTEAD OF fullName
        feedback.setFullName(request.getName());
        feedback.setEmail(request.getEmail());
        feedback.setPhone(request.getPhone());
        feedback.setServiceType(request.getServiceType());
        feedback.setRating(request.getRating());
        feedback.setMessage(request.getMessage());

        // DEFAULT VALUES for fields not in FeedbackRequest
        feedback.setClientType(null);
        feedback.setInvestmentAmountRange(null);
        feedback.setBranch(null);
        feedback.setIsAnonymous(false);
        feedback.setIpAddress(ipAddress);
        feedback.setUserAgent(userAgent);
        feedback.setStatus("PENDING");

        Feedback saved = feedbackRepository.save(feedback);
        log.info("New feedback submitted with ID: {}", saved.getId());

        return mapToResponse(saved);
    }

    @Override
    public Page<FeedbackResponse> getAllFeedbacks(Pageable pageable) {
        return feedbackRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public FeedbackResponse getFeedbackById(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback with ID " + id + " not found"));
        return mapToResponse(feedback);
    }

    @Override
    @Transactional
    public FeedbackResponse updateStatus(Long id, String status, String responseMessage, Long respondedBy) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback with ID " + id + " not found"));

        feedback.setStatus(status.toUpperCase());
        feedback.setResponseMessage(responseMessage);
        feedback.setRespondedBy(respondedBy);
        feedback.setUpdatedAt(LocalDateTime.now());

        Feedback updated = feedbackRepository.save(feedback);
        log.info("Feedback ID {} status updated to {}", id, status);

        return mapToResponse(updated);
    }

    @Override
    public List<FeedbackResponse> getFeedbacksByStatus(String status) {
        return feedbackRepository.findByStatus(status.toUpperCase())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackResponse> getFeedbacksByServiceType(String serviceType) {
        return feedbackRepository.findByServiceType(serviceType)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackResponse> getFeedbacksByDateRange(LocalDateTime start, LocalDateTime end) {
        return feedbackRepository.findBySubmittedAtBetween(start, end)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackResponse> getFeedbacksByRating(Integer rating) {
        return feedbackRepository.findByRating(rating)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackResponse> searchFeedbacks(String keyword) {
        return feedbackRepository.searchByKeyword(keyword)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FeedbackStatisticsDTO getStatistics() {
        List<Feedback> allFeedbacks = feedbackRepository.findAll();

        long total = allFeedbacks.size();
        double avgRating = allFeedbacks.stream()
                .mapToInt(Feedback::getRating)
                .average()
                .orElse(0.0);

        int minRating = allFeedbacks.stream()
                .mapToInt(Feedback::getRating)
                .min()
                .orElse(0);

        int maxRating = allFeedbacks.stream()
                .mapToInt(Feedback::getRating)
                .max()
                .orElse(0);

        // Status counts
        Map<String, Long> statusCounts = new HashMap<>();
        for (Object[] result : feedbackRepository.countByStatus()) {
            statusCounts.put((String) result[0], (Long) result[1]);
        }

        // Rating counts
        Map<Integer, Long> ratingCounts = new HashMap<>();
        for (Object[] result : feedbackRepository.countByRating()) {
            ratingCounts.put((Integer) result[0], (Long) result[1]);
        }

        // Average rating by service
        Map<String, Double> avgRatingByService = new HashMap<>();
        for (Object[] result : feedbackRepository.averageRatingByServiceType()) {
            avgRatingByService.put((String) result[0], (Double) result[1]);
        }

        FeedbackStatisticsDTO stats = new FeedbackStatisticsDTO();
        stats.setTotalFeedbacks(total);
        stats.setAverageRating(avgRating);
        stats.setMinRating(minRating);
        stats.setMaxRating(maxRating);
        stats.setStatusCounts(statusCounts);
        stats.setRatingCounts(ratingCounts);
        stats.setAverageRatingByService(avgRatingByService);

        return stats;
    }

    @Override
    @Transactional
    public void deleteFeedback(Long id) {
        if (!feedbackRepository.existsById(id)) {
            throw new RuntimeException("Feedback with ID " + id + " not found");
        }
        feedbackRepository.deleteById(id);
        log.info("Feedback ID {} deleted", id);
    }

    @Override
    public Page<FeedbackResponse> getRecentFeedbacks(Pageable pageable) {
        return feedbackRepository.findRecentFeedbacks(pageable)
                .map(this::mapToResponse);
    }

    // ============================================
    // MAPPER METHODS
    // ============================================
    private FeedbackResponse mapToResponse(Feedback feedback) {
        FeedbackResponse response = new FeedbackResponse();
        response.setId(feedback.getId());

        // USING name INSTEAD OF fullName
        response.setFullName(feedback.getFullName());
        response.setEmail(feedback.getEmail());
        response.setPhone(feedback.getPhone());
        response.setServiceType(feedback.getServiceType());
        response.setRating(feedback.getRating());
        response.setMessage(feedback.getMessage());
        response.setStatus(feedback.getStatus());
        response.setResponseMessage(feedback.getResponseMessage());
        response.setSubmittedAt(feedback.getSubmittedAt());
        response.setUpdatedAt(feedback.getUpdatedAt());
        response.setClientType(feedback.getClientType());
        response.setInvestmentAmountRange(feedback.getInvestmentAmountRange());
        response.setBranch(feedback.getBranch());
        response.setIsAnonymous(feedback.getIsAnonymous());
        return response;
    }
}