package com.suggestion_box.controller;

import com.suggestion_box.dto.ApiResponse;
import com.suggestion_box.dto.FeedbackRequest;
import com.suggestion_box.dto.FeedbackResponse;
import com.suggestion_box.dto.FeedbackStatisticsDTO;
import com.suggestion_box.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/feedbacks")
@CrossOrigin(origins = "*")
@Tag(name = "Feedback Management", description = "Endpoints za kusimamia maoni ya wateja")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    // ============================================
    // SUBMIT NEW FEEDBACK
    // ============================================
    @Operation(
            summary = "Wasilisha maoni mapya",
            description = "Hii endpoint inatumika kuwasilisha maoni mapya kutoka kwa mteja"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Maoni yamewasilishwa kikamilifu",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Data si sahihi",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<ApiResponse<FeedbackResponse>> submitFeedback(
            @Parameter(description = "Data ya maoni", required = true)
            @Valid @RequestBody FeedbackRequest request,

            @Parameter(description = "IP address ya mtumiaji", hidden = true)
            @RequestHeader(value = "X-Forwarded-For", required = false) String ipAddress,

            @Parameter(description = "User agent ya browser", hidden = true)
            @RequestHeader(value = "User-Agent", required = false) String userAgent) {

        FeedbackResponse response = feedbackService.submitFeedback(request, ipAddress, userAgent);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Maoni yamewasilishwa kikamilifu", response));
    }

    // ============================================
    // GET ALL FEEDBACKS
    // ============================================
    @Operation(
            summary = "Pata maoni yote",
            description = "Hii endpoint inarudisha orodha ya maoni yote yenye pagination"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Maoni yamepatikana",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            )
    })
    @GetMapping
    public ResponseEntity<ApiResponse<Page<FeedbackResponse>>> getAllFeedbacks(
            @Parameter(description = "Pagination parameters")
            @PageableDefault(size = 20, sort = "submittedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<FeedbackResponse> responses = feedbackService.getAllFeedbacks(pageable);
        return ResponseEntity.ok(ApiResponse.success("Feedbacks retrieved successfully", responses));
    }

    // ============================================
    // GET FEEDBACK BY ID
    // ============================================
    @Operation(
            summary = "Pata maoni kwa ID",
            description = "Hii endpoint inarudisha maoni moja kwa kutumia ID yake"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Maoni yamepatikana",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Maoni hayakupatikana",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FeedbackResponse>> getFeedbackById(
            @Parameter(description = "ID ya maoni", required = true, example = "1")
            @PathVariable Long id) {
        FeedbackResponse response = feedbackService.getFeedbackById(id);
        return ResponseEntity.ok(ApiResponse.success("Feedback found", response));
    }

    // ============================================
    // UPDATE STATUS
    // ============================================
    @Operation(
            summary = "Badilisha hali ya maoni",
            description = "Hii endpoint inatumika kubadilisha hali ya maoni (PENDING, REVIEWED, RESOLVED, ARCHIVED)"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Hali imebadilishwa",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Maoni hayakupatikana",
                    content = @Content
            )
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<FeedbackResponse>> updateStatus(
            @Parameter(description = "ID ya maoni", required = true, example = "1")
            @PathVariable Long id,

            @Parameter(description = "Hali mpya", required = true, example = "RESOLVED",
                    schema = @Schema(allowableValues = {"PENDING", "REVIEWED", "RESOLVED", "ARCHIVED"}))
            @RequestParam String status,

            @Parameter(description = "Jibu la maoni", example = "Asante kwa maoni yako")
            @RequestParam(required = false) String responseMessage,

            @Parameter(description = "ID ya mfanyakazi aliyejibu", example = "1")
            @RequestParam(required = false) Long respondedBy) {

        FeedbackResponse response = feedbackService.updateStatus(id, status, responseMessage, respondedBy);
        return ResponseEntity.ok(ApiResponse.success("Status updated successfully", response));
    }

    // ============================================
    // GET BY STATUS
    // ============================================
    @Operation(
            summary = "Pata maoni kwa hali",
            description = "Hii endpoint inarudisha maoni yote yaliyo na hali maalum"
    )
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> getFeedbacksByStatus(
            @Parameter(description = "Hali ya maoni", required = true, example = "PENDING",
                    schema = @Schema(allowableValues = {"PENDING", "REVIEWED", "RESOLVED", "ARCHIVED"}))
            @PathVariable String status) {
        List<FeedbackResponse> responses = feedbackService.getFeedbacksByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Feedbacks by status", responses));
    }

    // ============================================
    // GET BY SERVICE TYPE
    // ============================================
    @Operation(
            summary = "Pata maoni kwa aina ya huduma",
            description = "Hii endpoint inarudisha maoni yote kwa aina maalum ya huduma"
    )
    @GetMapping("/service/{serviceType}")
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> getFeedbacksByServiceType(
            @Parameter(description = "Aina ya huduma", required = true, example = "Uwekezaji Muda Mrefu")
            @PathVariable String serviceType) {
        List<FeedbackResponse> responses = feedbackService.getFeedbacksByServiceType(serviceType);
        return ResponseEntity.ok(ApiResponse.success("Feedbacks by service type", responses));
    }

    // ============================================
    // GET BY RATING
    // ============================================
    @Operation(
            summary = "Pata maoni kwa rating",
            description = "Hii endpoint inarudisha maoni yote yenye rating maalum"
    )
    @GetMapping("/rating/{rating}")
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> getFeedbacksByRating(
            @Parameter(description = "Rating (1-5)", required = true, example = "5",
                    schema = @Schema(minimum = "1", maximum = "5"))
            @PathVariable Integer rating) {
        List<FeedbackResponse> responses = feedbackService.getFeedbacksByRating(rating);
        return ResponseEntity.ok(ApiResponse.success("Feedbacks by rating", responses));
    }

    // ============================================
    // GET BY DATE RANGE
    // ============================================
    @Operation(
            summary = "Pata maoni kwa kipindi cha muda",
            description = "Hii endpoint inarudisha maoni yote yaliyowasilishwa kati ya tarehe maalum"
    )
    @GetMapping("/date-range")
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> getFeedbacksByDateRange(
            @Parameter(description = "Tarehe ya kuanza", required = true, example = "2026-01-01T00:00:00")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,

            @Parameter(description = "Tarehe ya mwisho", required = true, example = "2026-12-31T23:59:59")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<FeedbackResponse> responses = feedbackService.getFeedbacksByDateRange(start, end);
        return ResponseEntity.ok(ApiResponse.success("Feedbacks by date range", responses));
    }

    // ============================================
    // GET STATISTICS
    // ============================================
    @Operation(
            summary = "Pata takwimu za maoni",
            description = "Hii endpoint inarudisha takwimu mbalimbali kuhusu maoni (jumla, wastani, n.k.)"
    )
    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<FeedbackStatisticsDTO>> getStatistics() {
        FeedbackStatisticsDTO stats = feedbackService.getStatistics();
        return ResponseEntity.ok(ApiResponse.success("Statistics retrieved", stats));
    }

    // ============================================
    // SEARCH FEEDBACKS
    // ============================================
    @Operation(
            summary = "Tafuta maoni",
            description = "Hii endpoint inatafuta maoni kwa kutumia keyword katika ujumbe"
    )
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> searchFeedbacks(
            @Parameter(description = "Neno la kutafuta", required = true, example = "bora")
            @RequestParam String keyword) {
        List<FeedbackResponse> responses = feedbackService.searchFeedbacks(keyword);
        return ResponseEntity.ok(ApiResponse.success("Search results", responses));
    }

    // ============================================
    // GET RECENT FEEDBACKS
    // ============================================
    @Operation(
            summary = "Pata maoni ya hivi karibuni",
            description = "Hii endpoint inarudisha maoni ya hivi karibuni kwa mpangilio wa muda"
    )
    @GetMapping("/recent")
    public ResponseEntity<ApiResponse<Page<FeedbackResponse>>> getRecentFeedbacks(
            @Parameter(description = "Pagination parameters")
            @PageableDefault(size = 10, sort = "submittedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<FeedbackResponse> responses = feedbackService.getRecentFeedbacks(pageable);
        return ResponseEntity.ok(ApiResponse.success("Recent feedbacks", responses));
    }

    // ============================================
    // DELETE FEEDBACK
    // ============================================
    @Operation(
            summary = "Futa maoni",
            description = "Hii endpoint inafuta maoni kwa kutumia ID yake"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Maoni yamefutwa",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Maoni hayakupatikana",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFeedback(
            @Parameter(description = "ID ya maoni", required = true, example = "1")
            @PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.ok(ApiResponse.success("Feedback deleted successfully", null));
    }
}