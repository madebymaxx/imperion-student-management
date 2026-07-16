package com.maxx.imperion.dto;

import jakarta.validation.constraints.*;

import java.util.Set;

public class CreateStudentRequestDto {
    @NotBlank(message = "Name cannot be null/Empty or blank")
    @Size(min = 2, max = 50, message = "Student name must be within 2 to 50 character long")
    private String name;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Student must be at least 18 years old")
    @Max(value = 100, message = "Age cannot exceed 100")
    private Integer age;

    @NotBlank(message = "Student email cannot be blank")
    @Email(message = "Student email must be valid")
    private String email;

    @NotNull(message = "Roll number is required")
    @Positive(message = "Roll number must be positive")
    private Integer rollno;

    @NotBlank(message = "Course is required")
    @Size(min = 2, max = 50, message = "Course must be between 2 and 50 characters")
    private String course;

    @NotEmpty(message = "At least one subject is required")
    private Set<
            @NotBlank(message = "Subject name cannot be blank")
            @Size(min = 2, max = 50, message = "Subject name must be between 2 and 50 characters")
                    String> subjects;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRollno() {
        return rollno;
    }

    public void setRollno(Integer rollno) {
        this.rollno = rollno;
    }

}
