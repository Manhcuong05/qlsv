package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Lecturer;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    List<Lecturer> findByLecturerNameContainingIgnoreCase(String name);
    Lecturer findByEmail(String email);
}
