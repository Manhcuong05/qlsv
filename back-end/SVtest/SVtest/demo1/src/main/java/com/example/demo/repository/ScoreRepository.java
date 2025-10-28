package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Score;

public interface ScoreRepository extends JpaRepository<Score, Long> {
}