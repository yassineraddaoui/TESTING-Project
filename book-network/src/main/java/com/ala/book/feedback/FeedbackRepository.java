package com.ala.book.feedback;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FeedbackRepository extends JpaRepository<Feedback,Integer> , JpaSpecificationExecutor<Feedback> {
}
