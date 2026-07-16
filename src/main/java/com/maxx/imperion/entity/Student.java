package com.maxx.imperion.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
// Auto-generates the primary key using the database.
// No need to provide the id in the request body for create (POST) requests.
    private Long id;

    private String name;
    private int age;
    private String email;
    private int rollno;
    private String course;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "student_subjects",
            joinColumns = @JoinColumn(name = "student_id")
    )
    @Column(name = "subject_name")
    private Set<String> subjects = new HashSet<>();
    private boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Set<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<String> subjects) {
        this.subjects = subjects;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRollno() {
        return this.rollno;
    }

    public void setRollno(int rollno) {
        this.rollno = rollno;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
