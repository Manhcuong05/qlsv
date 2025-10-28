package com.example.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Subject;
import com.example.demo.entity.SubjectStatus;
import com.example.demo.repository.SubjectRepository;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
    }

    @Override
    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public Subject updateSubject(Long id, Subject subjectDetails) {
        Subject subject = getSubjectById(id);
        subject.setSubjectName(subjectDetails.getSubjectName());
        subject.setLecturer(subjectDetails.getLecturer());
        subject.setCredits(subjectDetails.getCredits());
        subject.setRoom(subjectDetails.getRoom());
        subject.setStartTime(subjectDetails.getStartTime());
        subject.setEndTime(subjectDetails.getEndTime());
        subject.setStatus(subjectDetails.getStatus());

        return subjectRepository.save(subject);
    }

    @Override
    public void deleteSubject(Long id) {
        Subject subject = getSubjectById(id);
        subjectRepository.delete(subject);
    }

    @Override
    public List<Subject> getSubjectsByStatus(SubjectStatus status) {
        return subjectRepository.findByStatus(status);
    }
}
