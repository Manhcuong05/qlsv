package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.ExamSchedule;
import com.example.demo.repository.ExamScheduleRepository;

@Service
public class ExamScheduleService {

    private final ExamScheduleRepository repository;

    public ExamScheduleService(ExamScheduleRepository repository) {
        this.repository = repository;
    }

    public List<ExamSchedule> getAll() {
        return repository.findAll();
    }

    public ExamSchedule getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam schedule not found"));
    }

    public ExamSchedule create(ExamSchedule examSchedule) {
        return repository.save(examSchedule);
    }

    public ExamSchedule update(Long id, ExamSchedule updated) {
        ExamSchedule existing = getById(id);
        existing.setSubject(updated.getSubject());
        existing.setExamDate(updated.getExamDate());
        existing.setStartTime(updated.getStartTime());
        existing.setEndTime(updated.getEndTime());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
