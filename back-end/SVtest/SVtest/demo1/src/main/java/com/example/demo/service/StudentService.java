
package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Lấy danh sách sinh viên
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    // Lấy sinh viên theo ID
    public Student getById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên với ID: " + id));
    }

    // Thêm sinh viên
    public Student save(Student student) {
        // Nếu có ID và ID đã tồn tại thì báo lỗi
        if (student.getId() != null && studentRepository.existsById(student.getId())) {
            throw new IllegalStateException("Sinh viên với ID này đã tồn tại.");
        }
        return studentRepository.save(student);
    }

    // Cập nhật sinh viên
    public Student update(Long id, Student updatedStudent) {
        Student existingStudent = getById(id);
        existingStudent.setName(updatedStudent.getName());
        existingStudent.setAddress(updatedStudent.getAddress());
        existingStudent.setEmail(updatedStudent.getEmail());
        return studentRepository.save(existingStudent);
    }

    // Xoá sinh viên
    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy sinh viên với ID: " + id);
        }
        studentRepository.deleteById(id);
    }
}
