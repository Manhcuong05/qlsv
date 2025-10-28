package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.ExamSchedule;
import com.example.demo.service.ExamScheduleService;

@RestController
@RequestMapping("/api/exams")
@CrossOrigin("*")
public class ExamScheduleController {

    private final ExamScheduleService service;

    public ExamScheduleController(ExamScheduleService service) {
        this.service = service;
    }

    // Get all exam schedules
    @GetMapping
    public ResponseEntity<List<ExamSchedule>> getAll() {
        try {
            List<ExamSchedule> examSchedules = service.getAll();
            return new ResponseEntity<>(examSchedules, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get exam schedule by ID
    @GetMapping("/{id}")
    public ResponseEntity<ExamSchedule> getById(@PathVariable Long id) {
        try {
            ExamSchedule examSchedule = service.getById(id);
            return new ResponseEntity<>(examSchedule, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Create a new exam schedule
    @PostMapping
    public ResponseEntity<ExamSchedule> create(@RequestBody ExamSchedule examSchedule) {
        try {
            ExamSchedule createdSchedule = service.create(examSchedule);
            return new ResponseEntity<>(createdSchedule, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Update an existing exam schedule
    @PutMapping("/{id}")
    public ResponseEntity<ExamSchedule> update(@PathVariable Long id, @RequestBody ExamSchedule examSchedule) {
        try {
            ExamSchedule updatedSchedule = service.update(id, examSchedule);
            return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete an exam schedule by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
