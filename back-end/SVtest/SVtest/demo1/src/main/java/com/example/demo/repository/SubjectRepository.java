package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Subject;
import com.example.demo.entity.SubjectStatus;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByStatus(SubjectStatus status);
}
