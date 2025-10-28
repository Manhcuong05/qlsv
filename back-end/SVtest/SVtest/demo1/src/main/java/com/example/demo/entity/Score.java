
package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double bt1;
    private double gk1;
    private double ck1;
    private double avg1;
    @Column(columnDefinition = "NVARCHAR(50)")
    private String rank1;

    private double bt2;
    private double gk2;
    private double ck2;
    private double avg2;

    @Column(columnDefinition = "NVARCHAR(50)")
    private String rank2;

    private double bt3;
    private double gk3;
    private double ck3;
    private double avg3;

    @Column(columnDefinition = "NVARCHAR(50)")
    private String rank3;

    private double avgTotal;

    @Column(columnDefinition = "NVARCHAR(50)")
    private String rankTotal;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public void calculateAll() {
        avg1 = bt1 * 0.2 + gk1 * 0.3 + ck1 * 0.5;
        avg2 = bt2 * 0.2 + gk2 * 0.3 + ck2 * 0.5;
        avg3 = bt3 * 0.2 + gk3 * 0.3 + ck3 * 0.5;

        rank1 = getRank(avg1);
        rank2 = getRank(avg2);
        rank3 = getRank(avg3);

        avgTotal = (avg1 + avg2 + avg3) / 3.0;
        rankTotal = getRank(avgTotal);
    }

    private String getRank(double avg) {
        if (avg >= 8.0) return "Giỏi";
        if (avg >= 6.5) return "Khá";
        if (avg >= 5.0) return "Trung bình";
        return "Yếu";
    }

    // ==== Getters and Setters ====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getBt1() {
        return bt1;
    }

    public void setBt1(double bt1) {
        this.bt1 = bt1;
    }

    public double getGk1() {
        return gk1;
    }

    public void setGk1(double gk1) {
        this.gk1 = gk1;
    }

    public double getCk1() {
        return ck1;
    }

    public void setCk1(double ck1) {
        this.ck1 = ck1;
    }

    public double getAvg1() {
        return avg1;
    }

    public void setAvg1(double avg1) {
        this.avg1 = avg1;
    }

    public String getRank1() {
        return rank1;
    }

    public void setRank1(String rank1) {
        this.rank1 = rank1;
    }

    public double getBt2() {
        return bt2;
    }

    public void setBt2(double bt2) {
        this.bt2 = bt2;
    }

    public double getGk2() {
        return gk2;
    }

    public void setGk2(double gk2) {
        this.gk2 = gk2;
    }

    public double getCk2() {
        return ck2;
    }

    public void setCk2(double ck2) {
        this.ck2 = ck2;
    }

    public double getAvg2() {
        return avg2;
    }

    public void setAvg2(double avg2) {
        this.avg2 = avg2;
    }

    public String getRank2() {
        return rank2;
    }

    public void setRank2(String rank2) {
        this.rank2 = rank2;
    }

    public double getBt3() {
        return bt3;
    }

    public void setBt3(double bt3) {
        this.bt3 = bt3;
    }

    public double getGk3() {
        return gk3;
    }

    public void setGk3(double gk3) {
        this.gk3 = gk3;
    }

    public double getCk3() {
        return ck3;
    }

    public void setCk3(double ck3) {
        this.ck3 = ck3;
    }

    public double getAvg3() {
        return avg3;
    }

    public void setAvg3(double avg3) {
        this.avg3 = avg3;
    }

    public String getRank3() {
        return rank3;
    }

    public void setRank3(String rank3) {
        this.rank3 = rank3;
    }

    public double getAvgTotal() {
        return avgTotal;
    }

    public void setAvgTotal(double avgTotal) {
        this.avgTotal = avgTotal;
    }

    public String getRankTotal() {
        return rankTotal;
    }

    public void setRankTotal(String rankTotal) {
        this.rankTotal = rankTotal;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
