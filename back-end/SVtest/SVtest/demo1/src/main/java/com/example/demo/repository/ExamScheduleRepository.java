package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ExamSchedule;

public interface ExamScheduleRepository extends JpaRepository<ExamSchedule, Long> {
}
