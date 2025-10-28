package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "NVARCHAR(255)")
    private String lecturerName;

    @Column(nullable = false, columnDefinition = "NVARCHAR(1000)")
    private String content;

    @Column(nullable = false)
    private Long studentId;

    public Feedback() {}

    public Feedback(String lecturerName, String content, Long studentId) {
        this.lecturerName = lecturerName;
        this.content = content;
        this.studentId = studentId;
    }

    // Getter & Setter đầy đủ
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLecturerName() { return lecturerName; }
    public void setLecturerName(String lecturerName) { this.lecturerName = lecturerName; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
}
