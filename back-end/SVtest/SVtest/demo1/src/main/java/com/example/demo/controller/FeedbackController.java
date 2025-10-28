package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Feedback;
import com.example.demo.repository.FeedbackRepository;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackRepository feedbackRepository;

    // GET all feedback
    @GetMapping
    public List<Feedback> getAll() {
        return feedbackRepository.findAll();
    }

    // GET feedback by student id
    @GetMapping("/student/{studentId}")
    public List<Feedback> getByStudent(@PathVariable Long studentId) {
        return feedbackRepository.findByStudentId(studentId);
    }

    // GET feedback by lecturer name (search)
    @GetMapping("/lecturer")
    public List<Feedback> getByLecturer(@RequestParam String name) {
        return feedbackRepository.findByLecturerNameContainingIgnoreCase(name);
    }

    // POST feedback
    @PostMapping
    public Feedback create(@RequestBody Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    // DELETE feedback (admin mới dùng)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Long id) {
        Optional<Feedback> feedback = feedbackRepository.findById(id);
        if (feedback.isPresent()) {
            feedbackRepository.deleteById(id);
            return ResponseEntity.ok().body("Đã xóa feedback id: " + id);
        } else {
            return ResponseEntity.status(404).body("Không tìm thấy feedback!");
        }
    }
}
