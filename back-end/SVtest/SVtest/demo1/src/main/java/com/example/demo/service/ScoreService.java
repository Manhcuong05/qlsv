
package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Score;
import com.example.demo.entity.Student;
import com.example.demo.repository.ScoreRepository;
import com.example.demo.repository.StudentRepository;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final StudentRepository studentRepository;

    public ScoreService(ScoreRepository scoreRepository, StudentRepository studentRepository) {
        this.scoreRepository = scoreRepository;
        this.studentRepository = studentRepository;
    }

    public List<Score> getAll() {
        return scoreRepository.findAll();
    }

    public Score getById(Long id) {
        return scoreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy điểm"));
    }

    public Score create(Score score) {
        // Lấy sinh viên từ database dựa theo ID
        Long studentId = score.getStudent().getId();
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên với ID = " + studentId));
        score.setStudent(student);

        score.calculateAll();
        return scoreRepository.save(score);
    }

    public Score update(Long id, Score updatedScore) {
        Score score = getById(id);

        score.setBt1(updatedScore.getBt1());
        score.setGk1(updatedScore.getGk1());
        score.setCk1(updatedScore.getCk1());

        score.setBt2(updatedScore.getBt2());
        score.setGk2(updatedScore.getGk2());
        score.setCk2(updatedScore.getCk2());

        score.setBt3(updatedScore.getBt3());
        score.setGk3(updatedScore.getGk3());
        score.setCk3(updatedScore.getCk3());

        // Nếu có thay đổi sinh viên → lấy lại từ DB
        if (updatedScore.getStudent() != null && updatedScore.getStudent().getId() != null) {
            Student student = studentRepository.findById(updatedScore.getStudent().getId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên với ID = " + updatedScore.getStudent().getId()));
            score.setStudent(student);
        }

        score.calculateAll();
        return scoreRepository.save(score);
    }

    public void delete(Long id) {
        scoreRepository.deleteById(id);
    }
}
