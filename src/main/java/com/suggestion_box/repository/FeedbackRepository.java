package com.suggestion_box.repository;

import com.suggestion_box.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    // ============================================
    // ADD THIS METHOD
    // ============================================
    List<Feedback> findByStatus(String status);

    // ============================================
    // ADD THESE METHODS TOO (if not exists)
    // ============================================
    Page<Feedback> findByStatus(String status, Pageable pageable);

    List<Feedback> findByEmail(String email);

    Optional<Feedback> findByEmailAndId(String email, Long id);

    List<Feedback> findByRating(Integer rating);

    List<Feedback> findByRatingBetween(Integer minRating, Integer maxRating);

    List<Feedback> findByServiceType(String serviceType);

    List<Feedback> findBySubmittedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT f.status, COUNT(f) FROM Feedback f GROUP BY f.status")
    List<Object[]> countByStatus();

    @Query("SELECT f.serviceType, AVG(f.rating) FROM Feedback f GROUP BY f.serviceType")
    List<Object[]> averageRatingByServiceType();

    @Query("SELECT f FROM Feedback f WHERE LOWER(f.message) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Feedback> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT f FROM Feedback f ORDER BY f.submittedAt DESC")
    Page<Feedback> findRecentFeedbacks(Pageable pageable);

    @Query("SELECT f.rating, COUNT(f) FROM Feedback f GROUP BY f.rating ORDER BY f.rating")
    List<Object[]> countByRating();

    boolean existsByEmail(String email);

    List<Feedback> findByStatusAndServiceType(String status, String serviceType);
}