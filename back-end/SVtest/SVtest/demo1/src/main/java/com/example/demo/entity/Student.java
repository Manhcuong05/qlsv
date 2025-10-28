
package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {

    @Id
    // Không dùng @GeneratedValue -> bạn sẽ tự gán id khi tạo mới
    private Long id;

    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String name;

    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String address;

    @Column(nullable = false, unique = true, columnDefinition = "nvarchar(255)")
    private String email;

    // Constructors
    public Student() {}

    public Student(Long id, String name, String address, String email) {
        this.id = id;  // tự truyền vào
        this.name = name;
        this.address = address;
        this.email = email;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id; // bạn phải set id thủ công
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
