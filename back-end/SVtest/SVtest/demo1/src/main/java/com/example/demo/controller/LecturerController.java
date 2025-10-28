package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Lecturer;
import com.example.demo.repository.LecturerRepository;

@RestController
@RequestMapping("/api/lecturers")
public class LecturerController {

    @Autowired
    private LecturerRepository lecturerRepository;

    // Lấy danh sách giảng viên
    @GetMapping
    public List<Lecturer> getAllLecturers() {
        return lecturerRepository.findAll();
    }

    // Lấy giảng viên theo ID
    @GetMapping("/{id}")
    public Lecturer getLecturerById(@PathVariable Long id) {
        return lecturerRepository.findById(id).orElse(null);
    }

    // Tìm kiếm theo tên
    @GetMapping("/search")
    public List<Lecturer> searchByName(@RequestParam String name) {
        return lecturerRepository.findByLecturerNameContainingIgnoreCase(name);
    }

    // Thêm mới giảng viên
    @PostMapping
    public Lecturer addLecturer(@RequestBody Lecturer lecturer) {
        return lecturerRepository.save(lecturer);
    }

    // Sửa thông tin giảng viên
    @PutMapping("/{id}")
    public Lecturer updateLecturer(@PathVariable Long id, @RequestBody Lecturer updatedLecturer) {
        Lecturer lecturer = lecturerRepository.findById(id).orElse(null);
        if (lecturer != null) {
            lecturer.setLecturerName(updatedLecturer.getLecturerName());
            lecturer.setEmail(updatedLecturer.getEmail());
            lecturer.setPhone(updatedLecturer.getPhone());
            lecturer.setDepartment(updatedLecturer.getDepartment());
            return lecturerRepository.save(lecturer);
        }
        return null;
    }

    // Xoá giảng viên
    @DeleteMapping("/{id}")
    public void deleteLecturer(@PathVariable Long id) {
        lecturerRepository.deleteById(id);
    }
}
