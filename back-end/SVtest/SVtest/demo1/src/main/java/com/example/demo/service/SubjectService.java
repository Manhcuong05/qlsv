package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Subject;
import com.example.demo.entity.SubjectStatus;

public interface SubjectService {
    List<Subject> getAllSubjects();
    Subject getSubjectById(Long id);
    Subject createSubject(Subject subject);
    Subject updateSubject(Long id, Subject subject);
    void deleteSubject(Long id);
    List<Subject> getSubjectsByStatus(SubjectStatus status);
}
