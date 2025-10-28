
package com.example.demo.dto;

public class LoginRequest {
    private String email;
    private String password;
    private String role;  // Thêm trường role

    // Constructor
    public LoginRequest(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getter và Setter cho email, password và role
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;  // Getter cho role
    }

    public void setRole(String role) {
        this.role = role;
    }
}
